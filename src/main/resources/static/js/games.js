function receiveData(data) {
	pageTotalCount = (Math.floor(data.TotalRecordCount/11)+1);
	StartIndex = data.StartIndex+1;
	$("#games-selector-page")[0].max = pageTotalCount;
	$("#games-selector-page")[0].value = (Math.floor(data.StartIndex/11)+1);
	$("#currentPageIndex")[0].textContent= window.history_msg_pageselector1 + StartIndex + window.history_msg_pageselector2 + pageTotalCount + window.history_msg_pageselector3;
	$("#currentPageIndex1")[0].textContent= window.history_msg_pageselector1;
	$("#currentPageIndex2")[0].textContent= window.history_msg_pageselector2 + pageTotalCount + window.history_msg_pageselector3;

	newBody = "";
	$.each(data.Records, function(i, row) {
		var newRow = "<tr>" + 
						"<td><a title='" + row.id + "' href='#' class='btn-detail ui-btn ui-icon-bullets ui-btn-icon-notext ui-corner-all'>No text</a></td>" +
						"<td>" + row.id + "</td>" + 
						"<td>" + row.time + "</td>" + 
						"<td><a href='#' class='btn-delete ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all'>No text</a></td>"
					 "</tr>"
		newBody = newBody + newRow;
	 });
	$("#tbProp").empty().append(newBody);
	$('#table-games').table("refresh");
	
	$(".btn-detail").on("click", function(event) {
		var gameId = $(this).attr("title");
		window.location.href = "/gameStaticalData?gameId=" + gameId;
    })
    
    $(".btn-delete").on("click", function(event) {
    	$("#delete-input-id")[0].value = $(this).parent().parent().children()[1].textContent;
		$("#delete-input-time")[0].value = $(this).parent().parent().children()[2].textContent;
		$("#dialog-games-delete").popup("open");
    })
}

var StartIndex = 1;
var pageTotalCount = 1;
$(document).on('pageinit', '#games', function(){
    $.post("/games/getGames?jtStartIndex=0&jtPageSize=10", null, receiveData);
    
    $( "#games-btn-display-page" ).on( "click", function( event ) {
		var page = $("#games-selector-page").val();
		if(page > pageTotalCount) {
			page = pageTotalCount;
		}
		$.post("/games/getGames?jtStartIndex=" + (page-1) + "&jtPageSize=10", null, receiveData);
		$(this).parent().popup("close");
	});
    
    $( "#btn-create-game" ).on( "click", function( event ) {
		$.post("/games/createGame", null, receiveData);
		$(this).parent().popup("close");
	});
    
    $( "#btn-delete-game" ).on( "click", function( event ) {
		$.post("/games/removeGame?id=" + $("#delete-input-id")[0].value, null, receiveData);
		$(this).parent().popup("close");
	});
});