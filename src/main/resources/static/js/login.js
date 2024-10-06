// /static/js/login.js
const loginBtn = document.getElementById('loginBtn');

loginBtn.addEventListener('click', function () {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    // 입력값 검증
    if (!username || !password) {
        alert('아이디와 비밀번호를 모두 입력해주세요.');
        return;
    }

    fetch('/api/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/'; // 로그인 성공 시 메인 페이지로 리다이렉트
        } else {
            return response.json().then(data => {
                alert('로그인에 실패하였습니다: ' + data.message);
            });
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('로그인 요청 중 오류가 발생했습니다.');
    });
});