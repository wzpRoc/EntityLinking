function adSearch(obj, type){
	if(obj.value==0)return;
	addFormParaThenSubmit('searchType', type, 'id', obj.value);
}