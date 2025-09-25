const API_BASE_URL = 'http://localhost:8080/auth';

function showForm(formType) {
    document.querySelectorAll('.tab').forEach(tab => tab.classList.remove('active'));
    document.querySelectorAll('.form-container').forEach(form => form.classList.remove('active'));

    if (formType === 'login') {
        document.querySelector('.tab:first-child').classList.add('active');
        document.getElementById('loginForm').classList.add('active');
    } else {
        document.querySelector('.tab:last-child').classList.add('active');
        document.getElementById('registerForm').classList.add('active');
    }
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

function showToken(token) {
    const tokenInfo = document.getElementById('tokenInfo');
    const tokenText = document.getElementById('tokenText');

    tokenText.textContent = token;
    tokenInfo.style.display = 'block';
}

async function signIn() {
    const phone = document.getElementById('loginPhone').value;
    const password = document.getElementById('loginPassword').value;

    if (!phone || !password) {
        showMessage('Заполните все поля', false);
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/sign-in`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                phone: phone,
                password: password
            })
        });

        if (response.ok) {
            const data = await response.json();
            showMessage('Успешный вход!', true);
            // showToken(data.token || JSON.stringify(data));
            // Сохраняем токен в localStorage
            localStorage.setItem('jwtToken', data.token);
            window.location.href = 'profile.html';
        } else {
            const error = await response.text();
            showMessage(`Ошибка входа: ${error}`, false);
        }
    } catch (error) {
        showMessage('Ошибка сети: ' + error.message, false);
    }
}

async function signUp() {
    const firstName = document.getElementById('firstName').value;
    const city = document.getElementById('city').value;
    const age = parseInt(document.getElementById('age').value);
    const phone = document.getElementById('registerPhone').value;
    const password = document.getElementById('registerPassword').value;
    const gender = document.getElementById('gender').value;
    const role = document.getElementById('role').value;

    if (!firstName || !city || !age || !phone || !password) {
        showMessage('Заполните все обязательные поля', false);
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/sign-up`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: firstName,
                city: city,
                age: age,
                phone: phone,
                password: password,
                gender: gender,
                role: role
            })
        });

        if (response.ok) {
            const data = await response.json();
            showMessage('Успешная регистрация!', true);
            // showToken(data.token || JSON.stringify(data));
            // Переключаемся на форму входа
            showForm('login');
            // Сохраняем токен в localStorage
            localStorage.setItem('jwtToken', data.token);
            window.location.href = 'profile.html';
        } else {
            const error = await response.text();
            showMessage(`Ошибка регистрации: ${error}`, false);
        }
    } catch (error) {
        showMessage('Ошибка сети: ' + error.message, false);
    }
}

// Проверяем наличие токена при загрузке
window.addEventListener('load', () => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
        // showToken(token);
        // showMessage('Токен найден в localStorage', true);
    }

    function redirectToProfile() {
        window.location.href = 'profile.html';
    }
});