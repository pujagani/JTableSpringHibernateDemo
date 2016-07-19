var StudentResultJTable = {
	actions : {
		createAction : 'StudentResult/Save?action=1',
		updateAction : 'StudentResult/Save?action=2',
		deleteAction : 'StudentResult/Delete'
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
		course_id : {
			title : 'Course',
			width : '12%',
			type : 'hidden',
		},
		course_name : {
			title : 'Course',
			width : '12%',
		},
		exam_date : {
			title : 'Exam date',
			width : '30%',
			type : 'date',
			layoutBreak : true

		},
		degree : {
			title : 'Degree',
			width : '10%',
			options : ["AA", "BA", "BB", "CB", "CC", "DC", "DD", "FF"]
		}
	}
};
