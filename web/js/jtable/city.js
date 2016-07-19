var CityJTable = {
	title : 'City List',
	paging : true,
	pageSize : 10,
	sorting : true,
	defaultSorting : 'name ASC',
	actions : {
		listAction : 'City/List',
		createAction : 'City/Save?action=1',
		updateAction : 'City/Save?action=2',
		deleteAction : 'City/Delete'
	},
	fields : {
		id : {
			key : true,
			create : false,
			edit : false,
			list : false
		},
		name : {
			title : 'Name',
			width : '23%'
		},
	}
};
