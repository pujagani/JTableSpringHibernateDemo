var StudentPhoneJTable = {
	actions : {
		createAction : 'StudentPhone/Save?action=1',
		updateAction : 'StudentPhone/Save?action=2',
		deleteAction : 'StudentPhone/Delete'
	},
	fields : {
		student_id : {
			type : 'hidden',
		},
		id : {
			key : true,
			create : false,
			edit : false,
			list : false
		},
		phone_type : {
			title : 'Phone type',
			width : '30%',
			options : {
				'1' : 'Home phone',
				'2' : 'Office phone',
				'3' : 'Cell phone'
			}
		},
		phone_number : {
			title : 'Phone Number',
			width : '30%'
		},
		record_date : {
			title : 'Record date',
			width : '20%',
			type : 'date',
			create : false,
			edit : false
		}
	}
};
