const API_BASE_URL = 'http://localhost:8080/api';
const AUTH_URL = 'http://localhost:8080/auth';

// Переменные для поиска
let currentFilters = {};
let searchResults = [];
let currentProfileUser = null;
let likesList = [];

// Переменные для чата
let currentChatUser = null;
let chatMessages = [];
let currentUserData = null;
let stompClient = null;
let isWebSocketConnected = false;

// Проверка аутентификации при загрузке
document.addEventListener('DOMContentLoaded', function () {
    checkAuth();
    loadProfile();
    setupEditForm();
    setupFilterForm();
    connectWebSocket();
    setupChatEventListeners();
});

function checkAuth() {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        window.location.href = 'index.html';
        return;
    }
}

async function loadProfile() {
    const token = localStorage.getItem('jwtToken');

    try {
        const response = await fetch(`${API_BASE_URL}/profile`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const userData = await response.json();
            currentUserData = userData;
            displayUserData(userData);
            populateEditForm(userData);
        } else if (response.status === 401) {
            localStorage.removeItem('jwtToken');
            window.location.href = 'index.html';
        } else {
            showMessage('Ошибка загрузки профиля', false);
        }
    } catch (error) {
        showMessage('Ошибка сети: ' + error.message, false);
    }
}

function displayUserData(userData) {
    document.getElementById('firstName').textContent = userData.firstName || 'Не указано';
    document.getElementById('city').textContent = userData.city || 'Не указан';
    document.getElementById('age').textContent = userData.age ? userData.age + ' лет' : 'Не указан';
    document.getElementById('phone').textContent = userData.phone || 'Не указан';
    document.getElementById('gender').textContent = formatGender(userData.gender);
    document.getElementById('role').textContent = formatRole(userData.role);
    document.getElementById('lookingFor').textContent = formatLookingFor(userData.lookingFor);
}

function populateEditForm(userData) {
    document.getElementById('editFirstName').value = userData.firstName || '';
    document.getElementById('editCity').value = userData.city || '';
    document.getElementById('editAge').value = userData.age || '';
    document.getElementById('editGender').value = userData.gender || 'MALE';
    document.getElementById('editLookingFor').value = userData.lookingFor || 'FRIENDSHIP';
}

function formatGender(gender) {
    const genders = {
        'MALE': '👨 Мужской',
        'FEMALE': '👩 Женский',
        'OTHER': '⚧ Другой'
    };
    return genders[gender] || gender || 'Не указан';
}

function formatRole(role) {
    const roles = {
        'USER': '👤 Пользователь',
        'ADMIN': '👑 Администратор'
    };
    return roles[role] || role;
}

function formatLookingFor(lookingFor) {
    const lookingForMap = {
        'FRIENDSHIP': '🤝 Дружба',
        'SERIOUS_RELATIONSHIP': '💑 Серьёзные отношения',
        'FAMILY': '👨‍👩‍👧‍👦 Семья',
        'TALKING': '💬 Общение'
    };
    return lookingForMap[lookingFor] || lookingFor;
}

function setupEditForm() {
    const form = document.getElementById('editForm');
    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        await updateProfile();
    });
}

async function updateProfile() {
    const token = localStorage.getItem('jwtToken');
    const updateData = {
        firstName: document.getElementById('editFirstName').value,
        city: document.getElementById('editCity').value,
        age: parseInt(document.getElementById('editAge').value),
        gender: document.getElementById('editGender').value,
        lookingFor: document.getElementById('editLookingFor').value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/profile`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updateData)
        });

        if (response.ok) {
            const updatedData = await response.json();
            displayUserData(updatedData);
            showMessage('✅ Профиль успешно обновлен!', true);
        } else {
            showMessage('❌ Ошибка обновления профиля', false);
        }
    } catch (error) {
        showMessage('❌ Ошибка сети: ' + error.message, false);
    }
}

// Функции для модального окна поиска
function toggleFindPair() {
    const modal = document.getElementById('findPairModal');
    modal.style.display = modal.style.display === 'block' ? 'none' : 'block';
}

function closeModal() {
    document.getElementById('findPairModal').style.display = 'none';
}

window.addEventListener('click', function (event) {
    const modal = document.getElementById('findPairModal');
    if (event.target === modal) {
        closeModal();
    }
});

function setupFilterForm() {
    const form = document.getElementById('filterForm');
    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        await searchUsers();
    });
}

function getFilters() {
    const genderEl = document.getElementById('filterGender');
    const cityEl = document.getElementById('filterCity');
    const minAgeEl = document.getElementById('minAge');
    const maxAgeEl = document.getElementById('maxAge');
    const lookingForEl = document.getElementById('filterLookingFor'); // <-- обновлённый id

    return {
        gender: genderEl?.value && genderEl.value !== '' ? genderEl.value : null,
        city: cityEl?.value && cityEl.value.trim() !== '' ? cityEl.value.trim() : null,
        minAge: minAgeEl?.value ? parseInt(minAgeEl.value) : null,
        maxAge: maxAgeEl?.value ? parseInt(maxAgeEl.value) : null,
        lookingFor: lookingForEl?.value && lookingForEl.value !== '' ? lookingForEl.value : null
    };
}

async function searchUsers() {
    const token = localStorage.getItem('jwtToken');
    currentFilters = getFilters();

    document.getElementById('loading').style.display = 'flex';
    document.getElementById('resultsContainer').innerHTML = '';

    try {
        const queryParams = new URLSearchParams();
        Object.entries(currentFilters).forEach(([key, value]) => {
            if (value !== null && value !== '') {
                queryParams.append(key, value);
            }
        });

        const response = await fetch(`${API_BASE_URL}/recommend?${queryParams}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            searchResults = await response.json();
            displaySearchResults(searchResults);
        } else if (response.status === 401) {
            showMessage('❌ Необходима авторизация', false);
            logout();
        } else {
            const errorText = await response.text();
            showMessage('❌ Ошибка поиска пользователей: ' + errorText, false);
        }
    } catch (error) {
        showMessage('❌ Ошибка сети: ' + error.message, false);
    } finally {
        document.getElementById('loading').style.display = 'none';
    }
}

function displaySearchResults(users) {
    const container = document.getElementById('resultsContainer');

    if (!users || users.length === 0) {
        container.innerHTML = '<div class="no-results">😔 Пользователи по заданным критериям не найдены</div>';
        return;
    }

    container.innerHTML = users.map(user => `
        <div class="user-card" onclick="viewUserProfile(${user.id})">
            <div class="user-header">
                <div class="user-name">${user.firstName || 'Не указано'}</div>
                <div class="user-age">${user.age || '?'} лет</div>
            </div>
            
            <div class="user-details">
                <div class="user-detail">
                    <span class="detail-label">Город</span>
                    <span class="detail-value">${user.city || 'Не указан'}</span>
                </div>
                
                <div class="user-detail">
                    <span class="detail-label">Пол</span>
                    <span class="detail-value">${formatGender(user.gender)}</span>
                </div>
                
                ${user.lookingFor ? `
                <div class="user-detail">
                    <span class="detail-label">Цель знакомства</span>
                    <span class="detail-value">${formatLookingFor(user.lookingFor)}</span>
                </div>
                ` : ''}
            </div>
            
            <div class="user-actions">
                <button class="like-btn" onclick="event.stopPropagation(); likeUser(${user.id})">❤️ Лайк</button>
                <button class="message-btn" onclick="event.stopPropagation(); startChatWithUser(${user.id})">💬 Чат</button>
            </div>
        </div>
    `).join('');
}

function clearFilters() {
    document.getElementById('filterForm').reset();
    document.getElementById('resultsContainer').innerHTML =
        '<div class="no-results">🎯 Введите критерии поиска и нажмите "Поиск"</div>';
    searchResults = [];
    currentFilters = {};
}

async function likeUser(userId) {
    const token = localStorage.getItem('jwtToken');
    const user = searchResults.find(u => u.id == userId);

    try {
        const response = await fetch(`${API_BASE_URL}/like?targetUserId=${userId}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const message = await response.text();
            showMessage(`❤️ ${message}`, true);

            if (document.getElementById('likesSection').style.display === 'block') {
                loadLikes();
            }
        } else {
            showMessage('❌ Ошибка при отправке лайка', false);
        }
    } catch (error) {
        showMessage('❌ Ошибка сети: ' + error.message, false);
    }
}

// Функции для раздела симпатий
function toggleLikesSection() {
    const likesSection = document.getElementById('likesSection');
    const isVisible = likesSection.style.display === 'block';

    likesSection.style.display = isVisible ? 'none' : 'block';

    if (!isVisible) {
        loadLikes();
    }
}

async function loadLikes() {
    const token = localStorage.getItem('jwtToken');
    const likesContainer = document.getElementById('likesContainer');
    const likesLoading = document.getElementById('likesLoading');

    likesLoading.style.display = 'flex';
    likesContainer.innerHTML = '';

    try {
        const response = await fetch(`${API_BASE_URL}/like`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            likesList = await response.json();
            displayLikes(likesList);
        } else if (response.status === 401) {
            showMessage('❌ Необходима авторизация', false);
            logout();
        } else {
            showMessage('❌ Ошибка загрузки симпатий', false);
        }
    } catch (error) {
        showMessage('❌ Ошибка сети: ' + error.message, false);
    } finally {
        likesLoading.style.display = 'none';
    }
}

function displayLikes(likes) {
    const container = document.getElementById('likesContainer');

    if (!likes || likes.length === 0) {
        container.innerHTML = '<div class="no-likes">😔 У вас пока нет симпатий</div>';
        return;
    }

    container.innerHTML = likes.map(like => `
        <div class="like-item" onclick="viewUserFromLikes(${like.userId})">
            <div class="like-info">
                <div class="like-name">${like.userName || 'Неизвестный пользователь'}</div>
                <div class="like-details">
                    ${like.userAge ? `<span class="like-detail">${like.userAge} лет</span>` : ''}
                    ${like.userCity ? `<span class="like-detail">📍 ${like.userCity}</span>` : ''}
                    ${like.userGender ? `<span class="like-detail">${formatGender(like.userGender)}</span>` : ''}
                </div>
            </div>
            <div class="like-actions">
                <button class="chat-btn" onclick="event.stopPropagation(); startChatWithUserFromLikes(${like.userId})">
                    💬 Чат
                </button>
                <button class="unlike-btn" onclick="event.stopPropagation(); unlike(${like.id})">
                    ❌ Убрать лайк
                </button>
            </div>
        </div>
    `).join('');
}

async function unlike(likeId) {
    const token = localStorage.getItem('jwtToken');

    try {
        const response = await fetch(`${API_BASE_URL}/like/${likeId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const message = await response.text();
            showMessage(`✅ ${message}`, true);
            loadLikes();
        } else {
            showMessage('❌ Ошибка при удалении лайка', false);
        }
    } catch (error) {
        showMessage('❌ Ошибка сети: ' + error.message, false);
    }
}

// Функции для просмотра профиля
function viewUserProfile(userId) {
    openUserProfileModal();
    loadUserProfile(userId);
}

function viewUserFromLikes(userId) {
    openUserProfileModal();
    loadUserProfile(userId);
}

function openUserProfileModal() {
    document.getElementById('userProfileModal').style.display = 'block';
    document.getElementById('profileLoading').style.display = 'block';
    document.getElementById('profileContent').style.display = 'none';
}

function closeUserProfile() {
    document.getElementById('userProfileModal').style.display = 'none';
    currentProfileUser = null;
}

async function loadUserProfile(userId) {
    const token = localStorage.getItem('jwtToken');

    try {
        const response = await fetch(`${API_BASE_URL}/user/${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const user = await response.json();
            currentProfileUser = user;
            displayUserProfile(user);
        } else {
            showMessage('❌ Не удалось загрузить профиль', false);
            closeUserProfile();
        }
    } catch (error) {
        showMessage('❌ Ошибка загрузки профиля', false);
        closeUserProfile();
    }
}

function displayUserProfile(user) {
    document.getElementById('profileName').textContent = user.firstName || 'Неизвестно';
    document.getElementById('profileAge').textContent = user.age ? user.age + ' лет' : '';
    document.getElementById('profileCity').textContent = user.city || 'Не указан';
    document.getElementById('profileGender').textContent = formatGender(user.gender);
    document.getElementById('profileLookingFor').textContent = formatLookingFor(user.lookingFor);
    document.getElementById('profilePhone').textContent = user.phone || 'Не указан';

    document.getElementById('profileLoading').style.display = 'none';
    document.getElementById('profileContent').style.display = 'block';
}

function likeProfileUser() {
    if (currentProfileUser) {
        likeUser(currentProfileUser.id);
        closeUserProfile();
    }
}

function startChatWithProfileUser() {
    if (currentProfileUser) {
        startChatWithUser(currentProfileUser.id);
        closeUserProfile();
    }
}

// Функции для чата
function connectWebSocket() {
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('WebSocket connected: ' + frame);
        isWebSocketConnected = true;

        stompClient.subscribe('/user/queue/messages', function(message) {
            const chatMessage = JSON.parse(message.body);
            if (currentChatUser && chatMessage.senderId === currentChatUser.id) {
                chatMessages.push(chatMessage);
                displayChatMessages(chatMessages);
            }
        });
    }, function(error) {
        console.log('WebSocket connection error: ', error);
        isWebSocketConnected = false;
        setTimeout(connectWebSocket, 5000);
    });
}

function setupChatEventListeners() {
    const chatInput = document.getElementById('chatMessageInput');
    if (chatInput) {
        chatInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                sendChatMessage();
            }
        });
    }
}

async function startChatWithUser(userId) {
    const user = searchResults.find(u => u.id == userId);
    if (user) {
        openChat(user);
    } else {
        await loadUserForChat(userId);
    }
}

async function startChatWithUserFromLikes(userId) {
    const likedUser = likesList.find(like => like.userId === userId);
    if (likedUser) {
        const user = {
            id: likedUser.userId,
            firstName: likedUser.userName,
            age: likedUser.userAge,
            city: likedUser.userCity,
            gender: likedUser.userGender
        };
        openChat(user);
    } else {
        await loadUserForChat(userId);
    }
}

async function loadUserForChat(userId) {
    const token = localStorage.getItem('jwtToken');

    try {
        const response = await fetch(`${API_BASE_URL}/user/${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const user = await response.json();
            openChat(user);
        } else {
            showMessage('❌ Не удалось начать чат', false);
        }
    } catch (error) {
        showMessage('❌ Ошибка загрузки пользователя', false);
    }
}

function openChat(user) {
    currentChatUser = user;
    document.getElementById('chatUserName').textContent = user.firstName || 'Пользователь';
    document.getElementById('chatModal').style.display = 'block';

    loadChatHistory(user.id);

    setTimeout(() => {
        document.getElementById('chatMessageInput').focus();
    }, 100);
}

function closeChatModal() {
    document.getElementById('chatModal').style.display = 'none';
    currentChatUser = null;
    chatMessages = [];
    document.getElementById('chatMessageInput').value = '';
}

async function loadChatHistory(otherUserId) {
    if (!currentUserData || !otherUserId) return;

    const token = localStorage.getItem('jwtToken');
    const messagesContainer = document.getElementById('chatMessages');

    messagesContainer.innerHTML = '<div class="no-messages">Загрузка сообщений...</div>';

    try {
        const response = await fetch(`${API_BASE_URL}/chat/history/${currentUserData.id}/${otherUserId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const messages = await response.json();
            chatMessages = messages;
            displayChatMessages(messages);
        } else {
            messagesContainer.innerHTML = '<div class="no-messages">Не удалось загрузить историю сообщений</div>';
        }
    } catch (error) {
        messagesContainer.innerHTML = '<div class="no-messages">Ошибка загрузки сообщений</div>';
    }
}

function displayChatMessages(messages) {
    const container = document.getElementById('chatMessages');

    if (!messages || messages.length === 0) {
        container.innerHTML = '<div class="no-messages">Начните общение первым!</div>';
        return;
    }

    container.innerHTML = messages.map(message => {
        const isSent = message.senderId === currentUserData.id;
        return `
            <div class="chat-message ${isSent ? 'message-sent' : 'message-received'}">
                <div class="message-text">${message.content}</div>
                <span class="message-time">${formatMessageTime(message.sentAt)}</span>
            </div>
        `;
    }).join('');

    container.scrollTop = container.scrollHeight;
}

function formatMessageTime(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleTimeString('ru-RU', {
        hour: '2-digit',
        minute: '2-digit'
    });
}

async function sendChatMessage() {
    if (!currentChatUser || !currentUserData) return;

    const input = document.getElementById('chatMessageInput');
    const message = input.value.trim();

    if (!message) return;

    const token = localStorage.getItem('jwtToken');

    try {
        const response = await fetch(`${API_BASE_URL}/chat/send`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                senderId: currentUserData.id,
                receiverId: currentChatUser.id,
                content: message
            })
        });

        if (response.ok) {
            const result = await response.text();
            console.log('Message sent:', result);

            const newMessage = {
                senderId: currentUserData.id,
                receiverId: currentChatUser.id,
                content: message,
                sentAt: new Date().toISOString()
            };

            chatMessages.push(newMessage);
            displayChatMessages(chatMessages);
            input.value = '';
        } else {
            showMessage('❌ Ошибка отправки сообщения', false);
        }
    } catch (error) {
        showMessage('❌ Ошибка сети', false);
    }
}

// Обработчики закрытия модальных окон
window.addEventListener('click', function(event) {
    const userProfileModal = document.getElementById('userProfileModal');
    if (event.target === userProfileModal) {
        closeUserProfile();
    }

    const chatModal = document.getElementById('chatModal');
    if (event.target === chatModal) {
        closeChatModal();
    }
});

function logout() {
    localStorage.removeItem('jwtToken');
    window.location.href = 'index.html';
}

function showMessage(text, isSuccess) {
    const messageDiv = document.getElementById('message');
    messageDiv.textContent = text;
    messageDiv.className = isSuccess ? 'message success' : 'message error';
    messageDiv.style.display = 'block';

    setTimeout(() => {
        messageDiv.style.display = 'none';
    }, 5000);
}