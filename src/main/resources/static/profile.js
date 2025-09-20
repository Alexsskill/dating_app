const API_BASE_URL = 'http://localhost:8080/api';
const AUTH_URL = 'http://localhost:8080/auth';

// Переменные для поиска
let currentFilters = {};
let searchResults = [];

// Проверка аутентификации при загрузке
document.addEventListener('DOMContentLoaded', function () {
    checkAuth();
    loadProfile();
    setupEditForm();
    setupFilterForm();
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
        'FEMALE': '👩 Женский'
    };
    return genders[gender] || gender;
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
    return {
        gender: document.getElementById('filterGender').value || null,
        city: document.getElementById('filterCity').value || null,
        minAge: document.getElementById('minAge').value ? parseInt(document.getElementById('minAge').value) : null,
        maxAge: document.getElementById('maxAge').value ? parseInt(document.getElementById('maxAge').value) : null,
        lookingFor: document.getElementById('lookingFor').value || null
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

        // Исправленный endpoint для поиска пользователей
        const response = await fetch(`${API_BASE_URL}/recommend?${queryParams}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        console.log('Search URL:', `${API_BASE_URL}/recommend?${queryParams}`);
        console.log('Response status:', response.status);

        if (response.ok) {
            searchResults = await response.json();
            console.log('Search results:', searchResults);
            displaySearchResults(searchResults);
        } else if (response.status === 401) {
            showMessage('❌ Необходима авторизация', false);
            logout();
        } else if (response.status === 404) {
            showMessage('❌ Endpoint не найден. Проверьте URL', false);
            displayDemoResults();
        } else {
            const errorText = await response.text();
            console.error('Search error:', errorText);
            showMessage('❌ Ошибка поиска пользователей: ' + errorText, false);
            displayDemoResults();
        }
    } catch (error) {
        console.error('Network error:', error);
        showMessage('❌ Ошибка сети: ' + error.message, false);
        displayDemoResults();
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
        <div class="user-card">
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
                
                <div class="user-detail">
                    <span class="detail-label">Телефон</span>
                    <span class="detail-value">${user.phone || 'Не указан'}</span>
                </div>
                
                ${user.lookingFor ? `
                <div class="user-detail">
                    <span class="detail-label">Цель знакомства</span>
                    <span class="detail-value">${formatLookingFor(user.lookingFor)}</span>
                </div>
                ` : ''}
            </div>
            
            <div class="user-actions">
                <button class="like-btn" onclick="likeUser('${user.id}')">❤️ Лайк</button>
                <button class="message-btn" onclick="messageUser('${user.id}')">✉️ Написать</button>
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

function likeUser(userId) {
    const user = searchResults.find(u => u.id == userId);
    if (user) {
        showMessage(`❤️ Вы лайкнули ${user.firstName}`, true);
        // Здесь можно добавить вызов API для лайка
    }
}

function messageUser(userId) {
    const user = searchResults.find(u => u.id == userId);
    if (user) {
        showMessage(`✉️ Чат с ${user.firstName}`, true);
        // Здесь можно добавить логику открытия чата
    }
}

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

// Функция для отладки - проверка доступности endpoint
async function testEndpoint() {
    const token = localStorage.getItem('jwtToken');
    try {
        const response = await fetch(`${API_BASE_URL}/recommend`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
        console.log('Endpoint test:', response.status, response.statusText);
        return response.ok;
    } catch (error) {
        console.error('Endpoint test error:', error);
        return false;
    }
}