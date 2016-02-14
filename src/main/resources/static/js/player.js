function receiveData(data) {
	pageTotalCount = (Math.floor(data.TotalRecordCount/11)+1);
	StartIndex = data.StartIndex+1;
	$("#player-selector-page")[0].max = pageTotalCount;
	$("#player-selector-page")[0].value = (Math.floor(data.StartIndex/11)+1);;
	$("#currentPageIndex")[0].textContent= window.history_msg_pageselector1 + StartIndex + window.history_msg_pageselector2 + pageTotalCount + window.history_msg_pageselector3;
	$("#currentPageIndex1")[0].textContent= window.history_msg_pageselector1;
	$("#currentPageIndex2")[0].textContent= window.history_msg_pageselector2 + pageTotalCount + window.history_msg_pageselector3;

	newBody = "";
	$.each(data.Records, function(i, row) {
		var newRow = "<tr>" + 
						"<td>" + row.id + "</td>" + 
						"<td>" + row.username + "</td>" + 
						"<td><a class='btn-edit ui-btn ui-icon-edit ui-btn-icon-notext ui-corner-all'>No text</a></td>" + 
						"<td><a href='#' class='btn-delete ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all'>No text</a></td>"
					 "</tr>"
		newBody = newBody + newRow;
	 });
	$("#tbProp").empty().append(newBody);
	$('#table-player').table("refresh");
	
	$(".btn-edit").on("click", function(event) {
		$("#modify-input-id")[0].value = $(this).parent().parent().children()[0].textContent;
		$("#modify-input-username")[0].value = $(this).parent().parent().children()[1].textContent;
		$("#modify-input-pwd")[0].value = $(this).parent().parent().children()[3].textContent;
		$("#dialog-player-modify").popup("open");
    })
    
    $(".btn-delete").on("click", function(event) {
    	$("#delete-input-id")[0].value = $(this).parent().parent().children()[0].textContent;
		$("#delete-input-username")[0].value = $(this).parent().parent().children()[1].textContent;
		$("#delete-input-pwd")[0].value = $(this).parent().parent().children()[3].textContent;
		$("#dialog-player-delete").popup("open");
    })
}

var StartIndex = 1;
var pageTotalCount = 1;
$(document).on('pageinit', '#player', function(){
    $.post("/player/getPlayers?jtStartIndex=0&jtPageSize=10", null, receiveData);
    
    $( "#player-btn-display-page" ).on( "click", function( event ) {
		var page = $("#player-selector-page").val();
		if(page > pageTotalCount) {
			page = pageTotalCount;
		}
		$.post("/player/getPlayers?jtStartIndex=" + (page-1) + "&jtPageSize=10", null, receiveData);
		$(this).parent().popup("close");
	});
    
    $( "#btn-create-player" ).on( "click", function( event ) {
    	var username = $("#create-player-username").val();
    	var password = $("#create-player-passowrd").val();
		$.post("/player/createPlayer?username=" + username + "&password=" + password, null, receiveData);
		$(this).parent().popup("close");
	});
    
    $( "#btn-modify-player" ).on( "click", function( event ) {
    	var id = $("#modify-input-id").val();
    	var username = $("#modify-input-username").val();
    	var password = $("#modify-input-pwd").val();
		$.post("/player/updatePlayer?id=" + id + "&username=" + username + "&password=" + password, null, receiveData);
		$(this).parent().popup("close");
	});
    
    $( "#btn-delete-player" ).on( "click", function( event ) {
    	var id = $("#delete-input-id").val();
		$.post("/player/deletePlayer?id=" + id, null, receiveData);
		$(this).parent().popup("close");
	});
});