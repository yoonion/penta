const loginBtn = document.getElementById('loginBtn');

loginBtn.addEventListener('click', function () {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    // 입력값 검증
    if (!username || !password) {
        alert('아이디와 비밀번호를 모두 입력해주세요.');
        return;
    }

    // 공통 API 호출 함수 사용
    callApi('/api/user/login', 'POST', { username, password })
        .then(() => {
            window.location.href = '/'; // 로그인 성공 시 메인 페이지로 리다이렉트
        })
        .catch(error => {
            alert('로그인에 실패하였습니다: ' + error.message);
        });
});
