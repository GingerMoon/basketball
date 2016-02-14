function setCurrentCategory() {
	var current = $("#category_select")[0].getAttribute("current");
	categories =  $("#category_select").children();
	for(var i = 0; i < categories.length; i++) {
		if(categories[i].value == current) {
			categories[i].selected = true;
		}
	}
}

function cateSelectOnchange() {
	var category = $("#category_select")[0].value;
	window.location.href = "/admin/" + category;
}

$(document).ready(function() {
	setCurrentCategory();
	
	var createDishDialog = $("#dialog-form-create").dialog({
		autoOpen : false,
		height : 400,
		width : 550,
		modal : true,
		buttons : {
		}
	});
	
	var changeDishDialog = $("#dialog-form-change").dialog({
		autoOpen : false,
		height : 450,
		width : 550,
		modal : true,
		buttons : {
		}
	});

	var deleteDishDialog = $("#dialog-form-delete").dialog({
		autoOpen : false,
		height : 250,
		width : 300,
		modal : true,
		buttons : {
		}
	});
		
	$(".create-dish").button().on("click", function() {
		createDishDialog.dialog("open");
		var value = $("#category_select")[0].getAttribute("current");
		var thisCategory =  $("#category-create")[0];
		for(var i=0; i < thisCategory.options.length; i++)
	    {
	      if(thisCategory.options[i].value == value) {
	    	  thisCategory.selectedIndex = i;
		      break;
	      }
	    }
	});
	

	$(".change-dish").button().on("click", function() {
		changeDishDialog.dialog("open");
		
		var thisID = $(this).parent().parent().attr('id').replace('detail-','');
		$("#dialog-form-change input[name~='id']").val(thisID);
		
		var thisName = $(this).siblings('.item_name').html();
		$("#dialog-form-change input[name~='title']").val(thisName);
		
		var value = $("#category_select")[0].getAttribute("current");
		var thisCategory =  $("#category-change")[0];
		for(var i=0; i < thisCategory.options.length; i++)
	    {
	      if(thisCategory.options[i].value == value) {
	    	  thisCategory.selectedIndex = i;
		      break;
	      }
	    }
		
		var thisDescription= $(this).siblings('p').html();
		$("#dialog-form-change input[name~='description']").val(thisDescription);
		
		var thisPrice = $(this).siblings('.price').html();
		$("#dialog-form-change input[name~='price']").val(thisPrice);
	});
	
	$(".delete-dish").button().on("click", function() {
		deleteDishDialog.dialog("open");
		var thisID = $(this).parent().parent().attr('id').replace('detail-','');
		$("#dialog-form-delete input[name~='id']").val(thisID);
	});	
});

