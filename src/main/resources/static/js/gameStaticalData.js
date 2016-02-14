function receiveData(data) {
	var newBody = '';
	var conpyContent = "";
	$.each(data.Records, function(i, row) {
		var ratio = (Number(row.score)/(Number(row.miss)+Number(row.score))*100).toPrecision(4);
		var newRow = "<tr>" + 
						"<td>" + row.id + "</td>" +
						"<td>" + row.gameId + "</td>" + 
						"<td>" + row.playerId + "</td>" + 
						"<td>" + row.playerName + "</td>" + 
						"<td>" + 
							'<div class="ui-nodisc-icon ui-alt-icon"><!-- Classes added to the wrapper -->' + 
								'<a href="#" title="' + row.id + '" class="btn-miss-minus ui-mini ui-btn ui-shadow ui-corner-all ui-icon-minus ui-btn-icon-notext ui-btn-inline"></a>' +
								 row.miss + 
								'<a href="#" title="' + row.id + '" class="btn-miss-plus ui-btn ui-mini ui-shadow ui-corner-all ui-icon-plus ui-btn-icon-notext ui-btn-inline"></a>' +
							'</div>' + 
						"</td>" +
						
						"<td>" + 
							'<div class="ui-nodisc-icon ui-alt-icon"><!-- Classes added to the wrapper -->' + 
								'<a href="#" title="' + row.id + '" class="btn-score-minus ui-btn ui-mini ui-shadow ui-corner-all ui-icon-minus ui-btn-icon-notext ui-btn-inline"></a>' +
								row.score + 
								'<a href="#" title="' + row.id + '" class="btn-score-plus ui-btn ui-mini ui-shadow ui-corner-all ui-icon-plus ui-btn-icon-notext ui-btn-inline"></a>' +
							'</div>' + 
						"</td>" +
						
						"<td>" + ratio + "</td>" + 
						"<td>" + row.victory + "</td>" + 
						"<td><a href='#' class='btn-delete ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all'>No text</a></td>"
					 "</tr>"
		newBody = newBody + newRow;
		conpyContent = conpyContent + '<label>' + row.victory + ' ' + row.playerName + ' -- ' + ratio + '% -- ' + row.score + '|' + row.miss + '</label>';
	 });
	$("#tbProp").empty().append(newBody);
	$('#table-game-statical-data').table("refresh");
	$("#dialog-copy-content").empty().append(conpyContent);
	
	
	// miss
	$(".btn-miss-minus").on("click", function(event) {
		var url = "/gameStaticalData/updateMiss?gameId=" + gameId + "&rowId=" + this.getAttribute('title') + "&flag=minus";
		$.post(url, null, receiveData);
    });
	
	$(".btn-miss-plus").on("click", function(event) {
		var url = "/gameStaticalData/updateMiss?gameId=" + gameId + "&rowId=" + this.getAttribute('title') + "&flag=plus";
		$.post(url, null, receiveData);
    });
	
	// score
	$(".btn-score-minus").on("click", function(event) {
		var url = "/gameStaticalData/updateScore?gameId=" + gameId + "&rowId=" + this.getAttribute('title') + "&flag=minus";
		$.post(url, null, receiveData);
    });
	
	$(".btn-score-plus").on("click", function(event) {
		var url = "/gameStaticalData/updateScore?gameId=" + gameId + "&rowId=" + this.getAttribute('title') + "&flag=plus";
		$.post(url, null, receiveData);
    });
	
	// remove
    $(".btn-delete").on("click", function(event) {
    	$("#remove-input-id")[0].value = $(this).parent().parent().children()[0].textContent;
    	$("#remove-input-gameId")[0].value = $(this).parent().parent().children()[1].textContent;
    	$("#remove-input-playerId")[0].value = $(this).parent().parent().children()[2].textContent;
    	$("#remove-input-playerName")[0].value = $(this).parent().parent().children()[3].textContent;
		$("#dialog-player-remove").popup("open");
    });
}

var gameId = 0;
var playerStartIndex = 0;
var playerCount = 0;
$(document).on('pageinit', '#gameStaticalData', function(){
	gameId = $("#gameId").attr('title');
	
    $.get("/gameStaticalData/getGameStaticalData?gameId=" + gameId, null, receiveData);

    
    // get players for adding players
    $( "#pop-up-btn-add-player" ).on( "click", function( event ) {
    	$.post("/player/getPlayers?jtStartIndex=0&jtPageSize=10", null, function(data) {
    		var fieldset = $('#fieldset-player-add-player');
    		newBody = "<legend>Vertical:</legend>";
    		playerCount = data.TotalRecordCount;
    		$.each(data.Records, function(i, row) {
    			var newRow = "<input name='checkbox-player-" + row.id + "' value=" + row.id + " id='checkbox-player-" + row.id + "' type='checkbox'>" + 
    						 "<label for='checkbox-player-" + row.id + "'>" + row.id + " " + row.username + "</label>"
    			newBody = newBody + newRow;
    		 });
    		fieldset.empty().append(newBody);
    	});
		$("#dialog-add-player").popup("open");
	});

    // get players for changing victory
    $( "#pop-up-btn-change-victory" ).on( "click", function( event ) {
    	$.post("/gameStaticalData/getPlayers?gameId=" + gameId, null, function(data) {
    		var fieldset = $('#fieldset-player-change-victory');
    		newBody = "<legend>Vertical:</legend>";
    		playerCount = data.TotalRecordCount;
    		$.each(data.Records, function(i, row) {
    			var newRow = "<input name='checkbox-player-" + row.id + "' value=" + row.id + " id='checkbox-player-" + row.id + "' type='checkbox'>" + 
    						 "<label for='checkbox-player-" + row.id + "'>" + row.id + " " + row.username + "</label>"
    			newBody = newBody + newRow;
    		 });
    		fieldset.empty().append(newBody);
    	});
		$("#dialog-change-victory").popup("open");
	});
    
    // get previous players
    $( "#btn-get-player-previous" ).on( "click", function( event ) {
    	if(playerStartIndex > 0) playerStartIndex--;
    	$.post("/player/getPlayers?jtStartIndex=" + playerStartIndex + "&jtPageSize=10", null, function(data) {
    		var fieldset = $('#fieldset-player-add-player');
    		newBody = "<legend>Vertical:</legend>";
    		$.each(data.Records, function(i, row) {
    			var newRow = "<input name='checkbox-player-" + row.id + "' value=" + row.id + " id='checkbox-player-" + row.id + "' type='checkbox'>" + 
    						 "<label for='checkbox-player-" + row.id + "'>" + row.id + " " + row.username + "</label>"
    			newBody = newBody + newRow;
    		 });
    		fieldset.empty().append(newBody);
    	});
	});
    
    // get next players
    $( "#btn-get-player-next" ).on( "click", function( event ) {
    	if(playerStartIndex < playerCount/11) playerStartIndex++;
    	$.post("/player/getPlayers?jtStartIndex=" + playerStartIndex + "&jtPageSize=10", null, function(data) {
    		var fieldset = $('#fieldset-player-add-player');
    		newBody = "<legend>Vertical:</legend>";
    		$.each(data.Records, function(i, row) {
    			var newRow = "<input name='checkbox-player-" + row.id + "' value=" + row.id + " id='checkbox-player-" + row.id + "' type='checkbox'>" + 
    						 "<label for='checkbox-player-" + row.id + "'>" + row.id + " " + row.username + "</label>"
    			newBody = newBody + newRow;
    		 });
    		fieldset.empty().append(newBody);
    	});
	});
    
    // add players
    $( "#btn-add-players" ).on( "click", function( event ) {
    	var players = $("#fieldset-player-add-player").children();
    	var playerIds = "";
    	for (i = 0; i < players.length; i++) {
            if (players[i].checked) {
            	playerIds = playerIds + players[i].value + ":";
            }
        }
    	var url = "/gameStaticalData/addGameStaticalData?gameId=" + gameId + "&playerIds=" + playerIds; 
		$.post(url, null, receiveData);
		$(this).parent().popup("close");
	});
        
    // change victory
    $( ".btn-change-victory" ).on( "click", function( event ) {
    	var action = this.getAttribute('value');
    	var players = $("#fieldset-player-change-victory").children();
    	var playerIds = "";
    	for (i = 0; i < players.length; i++) {
            if (players[i].checked) {
            	playerIds = playerIds + players[i].value + ":";
            }
        }
    	var url = "/gameStaticalData/changeVictory?gameId=" + gameId + "&action=" + action + "&playerIds=" + playerIds; 
		$.post(url, null, receiveData);
		$(this).parent().popup("close");
	});
    
    // remove btn click
    $( "#btn-remove-player" ).on( "click", function( event ) {
    	var id = $("#remove-input-id").val();
		$.post("/gameStaticalData/removeGameStaticalData?gameId=" + gameId + "&id=" + id, null, receiveData);
		$(this).parent().popup("close");
	});
    
    // copy content btn click
    $( "#pop-up-btn-copy-content" ).on( "click", function( event ) {
		$("#dialog-copy-content").popup("open");
	});
});