// (1) 회원정보 수정
function update(userId, event) {
	event.preventDefault();
	
	let data = $("#profileUpdate").serialize();

	$.ajax({
		type: "PUT",
		url: "/api/update/"+userId,
		data: data,
		dataType: "json"
	}).done(res=>{
		alert(res.message);
		location.href="/user/"+userId;
	}).fail(error=> {
		let resJsonData = error.responseJSON;

		if(resJsonData.data == null) {
			alert(resJsonData.message);
		} else {
			alert(JSON.stringify(resJsonData.data));
		}
	});
}
