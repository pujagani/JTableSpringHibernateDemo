$(document).ready(function () {
	$.widget("pcj.formWidget", {
// Creating a widget. All functions required for widget are defined and called here.
		_create : function () {
		// this refers to the element on which the widget is called.
		// self.options wil give the variables or objects passed to the widgets.
		// Objects stored can then be accessed.
			var self = this;
			var options = self.options;
			var fields = options.fields;
			var $div = $('<div/>');
			var $baseElement = $(self.element);
		// Classes are added as in jTable to create the same form template.
			var $form = $('<form />')
				.addClass("jtable-dialog-form jtable-edit-form");
			self.$form = $form;
			var $maindiv = $('<div />');
        //Iterating over each field to be created in the form and calling appropriate method.
        // self will call the method defined in the widget.
			$.each(fields, function (fieldName, field) {
				if (field.value == null)
					field.value = "";
				if ("text" == field.type || "password" == field.type) {
					self.buildInputWithLabel($maindiv, field);
				} else if ("dropdown" == field.type) {
					self.buildSelectWithLabel($maindiv, field);
				} else if ("radio" == field.type) {
					self.buildRadioWithLabel($maindiv, field);
				}

			});
			$maindiv.appendTo($form);
			$form.appendTo($baseElement);
			var $dialogOpts = fields.dialogOpts;
			$form.dialog(fields.dialogOpts);
		},
        // Defining a function to build input with label.
        // Defining div tags as required to create field visually same as jTable form fields.
        // Similarly for each field type methods are defined and called.
		buildInputWithLabel : function ($parent, field) {
			var self = this;
			var $subdiv = $('<div />').addClass("jtable-input-field-container").appendTo($parent);
			$('<div />').addClass('jtable-input-label').html(field.label).appendTo($subdiv);
			var $subsubdiv = $('<div />').addClass("jtable-input jtable-text-input").appendTo($subdiv);
			self.buildInput($subsubdiv, field);
		},

		buildInput : function ($parent, field) {
			var $input = $('<input />')
				.attr({
					type : field.type,
					name : field.name,
					value : field.value
				})
				.appendTo($parent);
		},
		buildRadioWithLabel : function ($parent, field) {
			var self = this;
			var $subdiv = $('<div />').addClass("jtable-input-field-container").appendTo($parent);
			$('<div />').addClass('jtable-input-label').html(field.label).appendTo($subdiv);
			var $subsubdiv = $('<div />').addClass("jtable-input jtable-text-input").appendTo($subdiv);
			self.buildRadio($subsubdiv, field);

		},

		buildRadio : function ($parent, field) {
			for (var i = 0; i < field.options.length; i++) {
				var attrs = {
					type : "radio",
					name : field.name,
					value : field.options[i]
				};
				if (field.options[i] == field.value)
					attrs.checked = 'checked';
				var $input = $('<input />')
					.attr(attrs)
					.appendTo($parent);
				$('<span />').html(field.options[i]).appendTo($parent);
			}
		},

		buildSelectWithLabel : function ($parent, field) {
			var self = this;
			var $subdiv = $('<div />').addClass("jtable-input-field-container").appendTo($parent);
			$('<div />').addClass('jtable-input-label').html(field.label).appendTo($subdiv);
			var $subsubdiv = $('<div />').addClass("jtable-input jtable-text-input").appendTo($subdiv);
			self.buildSelect($subsubdiv, field);

		},
		buildSelect : function ($parent, field) {
			var $select = $('<select />').attr({
					name : field.name
				}).appendTo($parent);
			for (var i = 0; i < field.options.length; i++) {
				var attrs = {
					value : field.options[i]
				};
				if (field.options[i] == field.value)
					attrs.selected = 'selected';
				$('<option />')
				.attr(attrs)
				.html(field.options[i])
				.appendTo($select);
			}
		}
	});

});
