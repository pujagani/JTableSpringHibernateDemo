$(document).ready(function () {
// This section of the code extends the capability of jTable.
// jTable has default method _create.
// The default method is stored in the variable base, in order to be called later.
	var base = {
		_create : $.hik.jtable.prototype._create,
	};

	// Extending the create method.
	$.extend(true, $.hik.jtable.prototype, {

		_create : function () {
			var self = this;
			// This will call the original method in jTable.
			base._create.apply(this, arguments);

			self.options.base = {};
            // layoutBreak option breaks the form at that particular field.
            // This allows users to fit large no. of form fields in a dialog box.
			var layoutBreakExists = false;
			// Checking if layoutBreak is true for any field.
			// If it is then layoutBreak logic is called.
			$.each(self.options.fields, function (fieldName, field) {
				if (field.layoutBreak) {
					layoutBreakExists = true;
				}
			});
            // formCreated method of jTable is stored and called later.
            // Functionality of formCreated method is extended by introducing layout break and popup widget.
			self.options.base.formCreated = self.options.formCreated;

			self.options.formCreated = function (event, data) {
				self.options.base.formCreated.apply(self, arguments);
				if (layoutBreakExists)
					formLayoutSetup.apply(self, arguments);// layoutBreak logic is called.
				popUpSetup.apply(self, arguments); //popUp logic is called.
			};
		},
	});
});

$(document).ready(function () {
// Defining popUp widget.
// Popup is created for selecting cities or courses by opening their respective jTable.
	$.widget("pcj.popUp", {
        // All function required for popUp are defined here.
		_create : function () {
		// Storing the element on which the popUp is created.
		// The element (keyfield stores input field to make it read only) is first made read only.
		// Arrow indicating that options are available is added using css.
			var self = this;
			self.element.prop("readOnly", true);
			self.element.css({
				'background-image' : 'url(../css/img/arrow_down.png)',
				'background-repeat' : 'no-repeat',
				'background-position' : 'right',
				'background-color' : '#EEE'
			});
			// On clicking the field a function is called to open the popUp.
			self.element.click(function () {
				self.openFieldJTable();
			});

		},

		openFieldJTable : function () {
		    // popUpOpts is defined where the popUp widget is called.
			var self = this;
			var popUpOpts = self.options.popUpOpts;
			var selectionTable = popUpOpts.selectionTable;
            // selectionTable refers to the jTable to be opened as popUp.
			selectionTable.selecting = true;
			// Only selecting an element is allowed to make it similar to selecting an element from a dropdown list.
			// Hence all action field of jTable are nullified.
			selectionTable.actions.createAction = null;
			selectionTable.actions.updateAction = null;
			selectionTable.actions.deleteAction = null;
			var $div = $('<div />').appendTo('body');
			// If any field is selected then a function is called to populate the form field with the selected option.
			selectionTable.selectionChanged = function () {
				self.fieldSelected($div);
			};
			$div.jtable(selectionTable);
			$div.jtable('load');
			$div.dialog({
				minWidth : 600,
				title : 'Make a selection',
				position : {
					my : "top",
					at : "top+50",
					of : window
				}
			});
		},

		fieldSelected : function ($div) {
			var self = this;
			var popUpOpts = self.options.popUpOpts;
            // jTable provides a way to obtain the selected rows.
			var $selectedRows = $div.jtable('selectedRows');
            // The data of the selected row can be stored in the form field.
            // Example: If city Mumbai is selected then value of city_id and name are stored in city_id and city_name
            // variables of Student class.
			if ($selectedRows.length > 0) {
				$selectedRows.each(function () {
					var record = $(this).data('record');
					// Obtaining and storing the name and id field.
					$(self.element).val(record[popUpOpts.nameField]);
					self.options.$keyField.val(record[popUpOpts.idField]);
					$div.remove();
				});
			}
		}
	});
});

function formLayoutSetup(event, data) {
	var self = this;
    // data.form gives the form data from jTable form.
	var $form = data.form;
	var formType = data.formType;
	var $table = $('<table/>').appendTo($form);
	// Table is created to implement layoutBreak.
	var $tr = $('<tr/>').appendTo($table);
	var $td = $('<td/>').attr({
			valign : 'top'
		}).appendTo($tr);
	// getFieldContainerMap returns hashmap of name for each field and entire field contents.
	var fieldContainerMap = getFieldContainerMap($form);
	$.each(fieldContainerMap, function (fieldName, $field) {
	// Loop through each field and check if layoutBreak is  true.
	// If it is then create a new td and append to tr.
	// That way the fields  will be aligned horizontally.
		if (self.options.fields[fieldName].layoutBreak) {
			$td = $('<td/>').attr({
					valign : 'top'
				}).appendTo($tr);
		}
		$field.appendTo($td);
	});
}

function getFieldContainerMap($form) {
   // Obtaining hashmap
	var map = {};
	// finding fields from the form using the jTable classes defined to create the field.
	var fields = $form.find(".jtable-input-field-container");
	for (var i = 0; i < fields.length; i++) {
		var $field = $(fields[i]);
		// Each field has many divs and classes.
		// Finding a field of specific type.
		// If it exists then storing its name as key in hashmap.
		// Value of hashmap is entire field.
		// These steps were done to get the field name to store as key.
		var $input = $field.find("input, select, textarea");
		if ($input.length > 0) {
			var fieldName = $input[0].name;
			map[fieldName] = $field;
		}
	}
	return map;
}

function popUpSetup(event, data) {
// Similar to layoutSetup.
	var self = this;
	var $form = data.form;
	var formType = data.formType;
	var fieldContainerMap = getFieldContainerMap($form);

	$.each(fieldContainerMap, function (fieldName, $field) {
	// Checking if popUp widget is called for any of the fields.
	// If it is, then calling the popUp widget by passing the required parameters.
		if (self.options.fields[fieldName].popUpOpts) {
			var popUpOpts = self.options.fields[fieldName].popUpOpts;
			var $input = $field.find("input");
			var $keyField = $form.find('input[name=' + popUpOpts.keyField + ']');
			$input.popUp({
				popUpOpts : popUpOpts,
				$keyField : $keyField
			});
		}
	});
}
