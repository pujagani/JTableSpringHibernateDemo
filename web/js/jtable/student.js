var StudentJTable = {
	title : 'The Student List',
	defaultSorting : 'name ASC',
	actions : {
		listAction : 'Student/List',
		createAction : 'Student/Save?action=1',
		updateAction : 'Student/Save?action=2',
		deleteAction : 'Student/Delete'
	},
	fields : {
		id : {
			key : true,
			create : false,
			edit : false,
			list : false
		},
		phones : {
			title : '',
			width : '2%',
			sorting : false,
			edit : false,
			create : false,
		},
		exams : {
			title : '',
			width : '2%',
			sorting : false,
			edit : false,
			create : false,
		},
		name : {
			title : 'Name',
			width : '23%'
		},
		email : {
			title : 'Email address',
			list : false
		},
		password : {
			title : 'User Password',
			type : 'password',
			list : false
		},
		gender : {
			title : 'Gender',
			width : '13%',
			options : {
				'M' : 'Male',
				'F' : 'Female'
			}
		},
		city_id : {
			title : 'City',
			width : '12%',
			type : 'hidden',
		},
		city_name : {
			title : 'City',
			width : '12%',
		},
		birth_date : {
			title : 'Birth date',
			width : '15%',
			type : 'date',
		},
		education : {
			title : 'Education',
			list : false,
			type : 'radiobutton',
			options : {
				'1' : 'Primary school',
				'2' : 'High school',
				'3' : 'University'
			}
		},
		about : {
			title : 'About this person',
			type : 'textarea',
			list : false,
		},
		active_flg : {
			title : 'Status',
			width : '12%',
			type : 'checkbox',
			values : {
				'N' : 'Inactive',
				'Y' : 'Active'
			},
			defaultValue : 'Y'
		},
		record_date : {
			title : 'Record date',
			width : '15%',
			type : 'date',
			create : false,
			edit : false,
			sorting : false
		}
	}
}
