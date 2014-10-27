<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="org.springframework.context.annotation.Import"%>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.util.Collection" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--  <link rel="stylesheet" href='<c:url value="/static/css/login/style.css"></c:url>'>-->

<!-- jQuery -->
<script src='<c:url value="/static/js/control/jquery.min.js"></c:url>'></script>

<!-- Bootstrap -->
<link rel="stylesheet"
	href='<c:url value="/static/css/control/bootstrap.min.css"></c:url>' />
<script
	src='<c:url value="/static/js/control/bootstrap.min.js"></c:url>'></script>
	
<!-- Data table -->
<script class='jsbin' src='<c:url value="/static/js/control/jquery.dataTables.min.js"></c:url>'></script>
<link rel="stylesheet"
	href='<c:url value="/static/css/control/jquery.dataTables.css"></c:url>' />

<script type="text/javascript">
/*
*	Char counter
*/
// to display the text areas length
function len_display(Object,MaxLen,element){
    var len_remain = MaxLen+Object.value.length;
    if(len_remain >=0){
    	document.getElementById(element).value=len_remain; 
    }
}
</script>

<c:if test="${(pageName == 'stat') or (pageName == 'users')}">
<script type="text/javascript">
/* DOM table*/
$.extend( true, $.fn.dataTable.defaults, {
	"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
	"sPaginationType": "bootstrap",
	"oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	}
} );


/* Default class modification */
$.extend( $.fn.dataTableExt.oStdClasses, {
	"sWrapper": "dataTables_wrapper form-inline"
} );


/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{
	return {
		"iStart":         oSettings._iDisplayStart,
		"iEnd":           oSettings.fnDisplayEnd(),
		"iLength":        oSettings._iDisplayLength,
		"iTotal":         oSettings.fnRecordsTotal(),
		"iFilteredTotal": oSettings.fnRecordsDisplay(),
		"iPage":          oSettings._iDisplayLength === -1 ?
			0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
		"iTotalPages":    oSettings._iDisplayLength === -1 ?
			0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
	};
};


/* Bootstrap style pagination control */
$.extend( $.fn.dataTableExt.oPagination, {
	"bootstrap": {
		"fnInit": function( oSettings, nPaging, fnDraw ) {
			var oLang = oSettings.oLanguage.oPaginate;
			var fnClickHandler = function ( e ) {
				e.preventDefault();
				if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
					fnDraw( oSettings );
				}
			};

			$(nPaging).addClass('pagination').append(
				'<div style="background-color: #fff"><ul class="list-inline">'+
					'<li class="prev disabled"><a href="manager#">&larr; '+oLang.sPrevious+'</a></li>'+
					'<li class="next disabled"><a href="manager#">'+oLang.sNext+' &rarr; </a></li>'+
				'</ul></div>'
			);
			var els = $('a', nPaging);
			$(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
			$(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
		},

		"fnUpdate": function ( oSettings, fnDraw ) {
			var iListLength = 5;
			var oPaging = oSettings.oInstance.fnPagingInfo();
			var an = oSettings.aanFeatures.p;
			var i, ien, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

			if ( oPaging.iTotalPages < iListLength) {
				iStart = 1;
				iEnd = oPaging.iTotalPages;
			}
			else if ( oPaging.iPage <= iHalf ) {
				iStart = 1;
				iEnd = iListLength;
			} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
				iStart = oPaging.iTotalPages - iListLength + 1;
				iEnd = oPaging.iTotalPages;
			} else {
				iStart = oPaging.iPage - iHalf + 1;
				iEnd = iStart + iListLength - 1;
			}

			for ( i=0, ien=an.length ; i<ien ; i++ ) {
				// Remove the middle elements
				$('li:gt(0)', an[i]).filter(':not(:last)').remove();

				// Add the new list items and their event handlers
				for ( j=iStart ; j<=iEnd ; j++ ) {
					sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
					$('<li '+sClass+'><a href="manager#">'+j+'</a></li>')
						.insertBefore( $('li:last', an[i])[0] )
						.bind('click', function (e) {
							e.preventDefault();
							oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
							fnDraw( oSettings );
						} );
				}

				// Add / remove disabled classes from the static elements
				if ( oPaging.iPage === 0 ) {
					$('li:first', an[i]).addClass('disabled');
				} else {
					$('li:first', an[i]).removeClass('disabled');
				}

				if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
					$('li:last', an[i]).addClass('disabled');
				} else {
					$('li:last', an[i]).removeClass('disabled');
				}
			}
		}
	}
} );


/*
 * TableTools Bootstrap compatibility
 * Required TableTools 2.1+
 */
if ( $.fn.DataTable.TableTools ) {
	// Set the classes that TableTools uses to something suitable for Bootstrap
	$.extend( true, $.fn.DataTable.TableTools.classes, {
		"container": "DTTT btn-group",
		"buttons": {
			"normal": "btn",
			"disabled": "disabled"
		},
		"collection": {
			"container": "DTTT_dropdown dropdown-menu",
			"buttons": {
				"normal": "",
				"disabled": "disabled"
			}
		},
		"print": {
			"info": "DTTT_print_info modal"
		},
		"select": {
			"row": "active"
		}
	} );

	// Have the collection use a bootstrap compatible dropdown
	$.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
		"collection": {
			"container": "ul",
			"button": "li",
			"liner": "a"
		}
	} );
}


/* Table initialisation */

$(function(){

	$('#tableQuery').dataTable( {
		"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
		"sPaginationType": "bootstrap",
		"bProcessing": true,
		"oLanguage": {
			"sLengthMenu": "_MENU_ records per page"
		}
	} );
	
});

</script>
</c:if>

<c:if test="${pageSubName == 'statDetails'}">
	<script type="text/javascript">
	$(document).ready(function () {
	    var interval = 5000;   //number of mili seconds between each call
	    var refresh = function() {
	        $.ajax({
	            url: "<c:url value='/rest/getMessageCountsByStatuses/'></c:url>${campaignId}",
	            type: 'GET',
	            cache: false,
	            dataType: 'json',
	            success: function(html) {
	                $('#totalMessages').html(JSON.stringify(html['total']));
	                $('#sentMessages').html(JSON.stringify(html['sent']));
	                
	                $('#stateDelivered').html(JSON.stringify(html['delivered']));
	                $('#stateExpired').html(JSON.stringify(html['expired']));
	                $('#stateDeleted').html(JSON.stringify(html['deleted']));
	                $('#stateUndeliverable').html(JSON.stringify(html['undeliverable']));
	                $('#stateAccepted').html(JSON.stringify(html['accepted']));
	                $('#stateRejected').html(JSON.stringify(html['rejected']));
	                $('#stateUnknown').html(JSON.stringify(html['unknown']));
	                setTimeout(function() {
	                    refresh();
	                    console.debug(JSON.stringify(html));
	                }, interval);
	            }
	        });
	    };
	    refresh();
	});
	
	</script>
</c:if>

<c:if test="${pageName == 'smppSettings'}">
<script type="text/javascript">
$(document).ready(function(){
	var interval = 5000;   //number of mili seconds between each call
    var refresh = function() {
        $.ajax({
            url: "<c:url value='/rest/getSystemIdConnectionStatus/all'></c:url>",
            type: 'GET',
            cache: false,
            dataType: 'json',
            success: function(html) {
                $.each(html, function(index, value){
                	if(value == 'connected'){
                		$('#smppConnectionStatus-'+index).html('<span class="label label-success label-xs">'+value+'</span>');
                	}else{
                		$('#smppConnectionStatus-'+index).html('<span class="label label-danger label-xs">'+value+'</span>');
                	}
                });
                setTimeout(function() {
                    refresh();
                    console.debug(JSON.stringify(html));
                }, interval);
            }
        });
    };
    refresh();
});
</script>
</c:if>

<c:if test="${pageName == 'index'}">
<script type="text/javascript">
$(document).ready(function(){
	var interval = 5000;   //number of mili seconds between each call
    var refresh = function() {
        $.ajax({
            url: "<c:url value='/rest/getSystemIdBusyStatus/all'></c:url>",
            type: 'GET',
            cache: false,
            dataType: 'json',
            success: function(html) {
            	
            	$("[id^='status-']").html('<span class="label label-success label-xs">free</span>');
            	
                $.each(html, function(index, value){
                	if(index == 'all'){
                		$("[id^='status-']").html('<span class="label label-warning label-xs">'+value+'</span>');
                		return false;
                	}else{
                		$('#status-'+index).html('<span class="label label-warning label-xs">'+value+'</span>');
                	}
                });
                setTimeout(function() {
                    refresh();
                    console.debug(JSON.stringify(html));
                }, interval);
            }
        });
    };
    refresh();
});
</script>
</c:if>

</head>
<body>

	<%
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Object[] authorities = auth.getAuthorities().toArray();
	%>
	
	<div>
		<nav id="myNavbar" class="navbar navbar-inverse navbar-fixed-top"
			role="navigation"> <!-- Brand and toggle get grouped for better mobile display -->
		<div class="container">
			<div class="navbar-header">
				<!-- <img src="img/logo.png" alt="logo" width="60px" />-->
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" data-toggle="dropdown"
						class="dropdown-toggle">Menu<b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li><a href='<c:url value="/"></c:url>'>Create Campaign</a></li>
							<li><a href='<c:url value="/stat"></c:url>'>Statistics</a></li>

						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" data-toggle="dropdown"
						class="dropdown-toggle"><%= userName.toUpperCase() %><b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<c:if test="<%=authorities[0].toString().equals("ROLE_SU")%>">
								<li><a href='<c:url value="/users"></c:url>'>User
										Management</a></li>
							</c:if>
							<c:if test="<%=authorities[0].toString().equals("ROLE_SU") || authorities[0].toString().equals("ROLE_ADMIN")%>">
								<li><a href='<c:url value="/smpp_settings"></c:url>'>SMPP
										Settings</a></li>
								<li class="divider"></li>
							</c:if>
							<li><a href='<c:url value="/logout"></c:url>'>Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
		</nav>
	</div>

	<div style="padding: 20px 20px 20px 20px">
		<div
			style="margin-top: 60px; padding: 0px 20px 20px 20px; background-color: #94ADCA; border-style: solid; border-color: #2B4868; border-width: 1px;">

			<c:choose>
				<c:when test="${pageName=='index'}">
					<jsp:include page="create_campaign.jsp"></jsp:include>
				</c:when>
				<c:when test="${pageName=='stat'}">
					<jsp:include page="stat.jsp"></jsp:include>
				</c:when>
				<c:when test="${pageName=='smppSettings'}">
					<jsp:include page="smpp_settings.jsp"></jsp:include>
				</c:when>
				<c:when test="${pageName=='users'}">
					<jsp:include page="user_settings.jsp"></jsp:include>
				</c:when>
			</c:choose>

		</div>
	</div>
</body>
</html>