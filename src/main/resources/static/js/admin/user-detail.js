// 사용자 삭제
document.getElementById('deleteBtn').addEventListener('click', function () {

    if (confirm('정말로 삭제하시겠습니까?')) {
        // callApi 함수를 사용하여 삭제 API 요청
        callApi(`/api/users/${userId}`, 'DELETE')
            .then(() => {
                alert('삭제되었습니다.');
                location.href = '/admin/users'; // 회원 목록 페이지로 이동
            })
            .catch(error => {
                alert('오류 발생: ' + error.message);
            });
    }
});