function isAdmin() {
	if($(".accountInfo")[0].getAttribute("id") == "NO")
		return false;
	else
		return true;
}

function getXHRparams() {
	return {
		"accountId" : $('#accountId-txt').val()
	}
}

$(document).ready(function() {
	var categories = $(".categories");
	var menuItemCategoryOption = new Array(categories.length);
	for(var i = 0; categories.length > i; i++) {
		var element = new Object();
		element.Value = categories[i].value;
		element.DisplayText = categories[i].text;
		menuItemCategoryOption[i] = element;
	}
	
	var tableOptionsActions = {
			listAction : 'salesRecord/getSalesOrders?status=0',
			updateAction : 'salesRecord/updateSalesOrder',
			deleteAction : 'salesRecord/removeSalesOrder'
		};
	
	if(!isAdmin()) {
		$('#accountId-txt')[0].disabled = true; 
		delete tableOptionsActions.deleteAction;
		delete tableOptionsActions.updateAction;
	}
	
	var tableOptions = {
			title : window.salesRecord_SalesRecordTableContainer_orderTable + '(' + window.salesRecord_SalesRecordTableContainer_inProgress + ')',
			selecting : true, //Enable selecting 
			paging : true, //Enable paging
			pageSize : 10, //Set page size (default: 10)
			sorting : true, //Enable sorting
			jqueryuiTheme : true,
			openChildAsAccordion: true, //Enable this line to show child tabes as accordion style
			actions : tableOptionsActions,
			toolbar: {
			    items: [{
			        
			    }]
			},
			fields : {
				itmes: {
				    title: window.salesRecord_SalesRecordTableContainer_items,
				    width: '5%',
				    sorting: false,
				    edit: false,
				    create: false,
				    display: function (salesOrderData) {
				        //Create an image that will be used to open child table
				        var $img = $('<img src="../jtableResources/images/list_metro.png" title="Details" />');
				        //Open child table when user clicks the image
				        
						var tableOptionsChildActions = {
		                        listAction: '/salesRecord/getOrderItems?salesOrderId=' + salesOrderData.record.id,
		                        updateAction: '/salesRecord/updateOrderItems?salesOrderId=' + salesOrderData.record.id,
		                        deleteAction: '/salesRecord/removeOrderItems?salesOrderId=' + salesOrderData.record.id
		                    };
				        
						if(!isAdmin()) {
							delete tableOptionsChildActions.updateAction;
							delete tableOptionsChildActions.deleteAction;
						}
						
				        $img.click(function () {
				            $('#SalesRecordTableContainer').jtable('openChildTable',
				                    $img.closest('tr'), //Parent row
				                    {
				                    title: window.salesRecord_itemChildTable + salesOrderData.record.id,
				                    actions: tableOptionsChildActions,
				                    fields: {
				                        id: {
				                            key: true,
				                            create: false,
				                            edit: false,
				                            list: false
				                        },
				                        menuItemCategory	: {
				                            title: window.salesRecord_itemChildTable_category,
				                            options: menuItemCategoryOption,
											edit : false,
				                            width: '20%'
				                        },
				                        menuItemTitle: {
				                            title: window.salesRecord_itemChildTable_title,
											edit : false,
				                            width: '20%'
				                        },
				                        menuItemDescription: {
				                            title: window.salesRecord_itemChildTable_description,
											edit : false,
				                            width: '30%'
				                        },
				                        menuItemPrice: {
				                            title: window.salesRecord_itemChildTable_price,
											edit : false,
				                            width: '20%'
				                        },
				                        quantity: {
				                            title: window.salesRecord_itemChildTable_quantity,
				                            width: '20%'
				                        }
				                    },
				                    recordDeleted : function(event, data) {
										$('#SalesRecordTableContainer').jtable('load', getXHRparams());
									},
									recordUpdated : function(event, data) {
										$('#SalesRecordTableContainer').jtable('load', getXHRparams());
									}
				                }, function (data) { //opened handler
				                    data.childTable.jtable('load');
				                });
				        });
				        //Return image to show on the person row
				        return $img;
				    }
				},
				status : {
					title : window.salesRecord_SalesRecordTableContainer_status,
					options: {  '1': window.salesRecord_SalesRecordTableContainer_completed, 
							    '0': window.salesRecord_SalesRecordTableContainer_inProgress},
					width : '5%'
				},
				id : {
					title : window.salesRecord_SalesRecordTableContainer_orderId,
					key : true,
					list : true,
					create : false,
					edit : false
				},
				accountName : {
					title : window.salesRecord_SalesRecordTableContainer_accountName,
					width : '10%',
					edit: false,
				},
				accountId : {
					title : window.salesRecord_SalesRecordTableContainer_accountId,
					width : '10%',
					edit: false,
				},
				accountPosition : {
					title : window.salesRecord_SalesRecordTableContainer_accountPosition,
					width : '10%',
					edit: false,
				},
				totalPrice : {
					title : window.salesRecord_SalesRecordTableContainer_totalPrice,
					width : '10%',
					edit: false,
				},
				time : {
					title : window.salesRecord_SalesRecordTableContainer_time,
					width : '15%',
					edit: false,
				},
				description : {
					title : window.salesRecord_SalesRecordTableContainer_descripton
				}
			},
			recordsLoaded: function(event, data) {
			},
			recordAdded: function(event, data) {
				$('#SalesRecordTableContainer').jtable('load', getXHRparams());
			},
			recordUpdated: function(event, data) {
				$('#SalesRecordTableContainer').jtable('load', getXHRparams());
			}
		};

	$('#SalesRecordTableContainer').jtable(tableOptions);
	$('#LoadRecordsButton').click(function(e) {
		e.preventDefault();
		
		$('#SalesRecordTableContainer').remove();
    	$('.thymeleafContentContainer').append('<div id="SalesRecordTableContainer" style="width: 99%;"></div>');
    	var status = $("#sales-records-status").val();
    	tableOptionsActions.listAction = 'salesRecord/getSalesOrders?status=' + status;
    	tableOptions.actions = tableOptionsActions;
    	var tableOptions1 = tableOptions;
    	tableOptions1.title = window.salesRecord_SalesRecordTableContainer_orderTable + '(' + window.salesRecord_SalesRecordTableContainer_all + ')';
    	$('#SalesRecordTableContainer').jtable(tableOptions1);
    	$('#SalesRecordTableContainer').jtable('load', getXHRparams());
	});
	$('#SalesRecordTableContainer').jtable('load', getXHRparams());
	
	if(isAdmin()) {
		toastr.options = {
		  "closeButton": false,
		  "debug": false,
		  "newestOnTop": true,
		  "progressBar": false,
		  "positionClass": "toast-bottom-center",
		  "preventDuplicates": false,
		  "onclick": null,
		  "showDuration": "300",
		  "hideDuration": "1000",
		  "timeOut": "4500",
		  "extendedTimeOut": "1000",
		  "showEasing": "swing",
		  "hideEasing": "linear",
		  "showMethod": "fadeIn",
		  "hideMethod": "fadeOut"
		}
		var timerID = setInterval(function() { 
			var request = "/salesRecord/count?";
			var XHRparams = getXHRparams();
			XHRparams['status'] = '0';
			for(var key in XHRparams){  
				request += key + '=' + XHRparams[key] + '&';
            }
			$.get(request, function(data) {
				if(data != 0)
				toastr.info(window.salesRecord_newRecordsInprocess)
			});
		}, 5000);
	}
});
