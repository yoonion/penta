// /static/js/login.js
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); // 기본 폼 제출 방지

            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value;

            // 입력값 검증
            if (!username || !password) {
                alert('아이디와 비밀번호를 모두 입력해주세요.');
                return;
            }

            // form action을 통한 폼 제출
            this.submit();
        });
    }
});
