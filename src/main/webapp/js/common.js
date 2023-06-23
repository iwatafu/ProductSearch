function formcheck() {
	if (document.searchform.search.value == "") {
		alert("検索ワードを入力してください。");
		return false;
	}
}