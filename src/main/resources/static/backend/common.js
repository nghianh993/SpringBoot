/*
* Create 09/04/2018
* NghiaN
*/
var root = "";
var pageSize = 20;
var timeoutTime = 2000;
var fis_vnp_js = (function () {
    var fis_vnp_js_common = function () { };

    fis_vnp_js_common.prototype.postDataAndReloadPage = function(link, data, messageSS, messageEr){
    	$.LoadingOverlay("show");
    	$.post(link, data, function(resp){
    		$.LoadingOverlay("hide");
			if(resp.result){
				fis_vnp_js.fis_vnp().notifyAutoHideNotify(messageSS, "success");
				setTimeout(function() {
					location.reload();
				}, timeoutTime);
			}else{
				fis_vnp_js.fis_vnp().notifyAutoHideNotify(messageEr, "error");
			}
    	});
    },

    fis_vnp_js_common.prototype.postDataAndReload = function(link, data, dataReload, messageSS, messageEr){
    	$.LoadingOverlay("show");
    	$.post(link, data, function(resp){
    		$.LoadingOverlay("hide");
			if(resp.result){
				fis_vnp_js.fis_vnp().notifyAutoHideNotify(messageSS, "success");
                dataReload();
			}else{
				fis_vnp_js.fis_vnp().notifyAutoHideNotify(messageEr, "error");
			}
    	});
    },

    //paging all page
    fis_vnp_js_common.prototype.loadDataAll = function (linkApi, tagAppend, data) {
        $.LoadingOverlay("show");
        $.post(linkApi, data, function (resp) {
            if (resp != null) {
                $("#" + tagAppend + " tbody").html("");
                $("#" + tagAppend + " tbody").html(resp);
                if (typeof $(".page-home").val() != "undefined") {
                    $(".page-home").show();
                } else {
                    var html = '<div class="page-home lo-paging"><div class="col-xs-12 col-md-4 form-inline"><label>Hiển thị <select class="ddlpage form-control"><option value="2">2</option><option value="20">20</option><option value="50">50</option><option value="100">100</option><option value="150">150</option><option value="200">200</option></select> tin</label></div><div class="col-xs-12 col-md-8 lo-paging-0"><div class="dataTables_paginate homepagging "><div class="pagination" id="pagination"></div></div></div></div>';
                    $(".pagecus").append(html);
                }
                $.LoadingOverlay("hide");
            }
        });
    },

    fis_vnp_js_common.prototype.loadDataAndPagingAll = function (linkApi, tagAppend, data) {
        $.LoadingOverlay("show");
        $.post(linkApi, data, function (resp) {
            if (resp != null) {
                $("#" + tagAppend + " tbody").html("");
                $("#" + tagAppend + " tbody").html(resp);
                var totalpage = parseInt($("#" + tagAppend + " tbody").find('#datatable').attr('data-total'));
                if (totalpage > 1) {
                    if (typeof $(".page-home").val() != "undefined") {
                        $(".page-home").show();
                    } else {
                        var html = '<div class="page-home lo-paging"><div class="col-xs-12 col-md-4 form-inline"><label>Hiển thị <select class="ddlpage form-control"><option value="2">2</option><option value="20">20</option><option value="50">50</option><option value="100">100</option><option value="150">150</option><option value="200">200</option></select> tin</label></div><div class="col-xs-12 col-md-8 lo-paging-0"><div class="dataTables_paginate homepagging "><div class="pagination" id="pagination"></div></div></div></div>';
                        $(".pagecus").append(html);
                    }
                    fis_vnp_js.fis_vnp().showPaginationAll(totalpage, linkApi, tagAppend, data);
                } else {
                    $(".page-home").hide();
                }
                $.LoadingOverlay("hide");
            }
        });
    },

    fis_vnp_js_common.prototype.showPaginationAll = function (pagesCounter, linkApi, tagAppend, data) {
        $('#pagination').remove();
        $('.homepagging').html('<div class="pagination" id="pagination"></div>');
        $('#pagination').twbsPagination({
            totalPages: pagesCounter,
            visiblePages: 3,
            first: 'Trang đầu',
            prev: 'Trước',
            next: 'Tiếp',
            last: 'Trang cuối',
            onPageClick: function (event, page) {
                var curentpage = parseInt($('#datatable').attr("data-page"));
                if (curentpage != page) {
                    data.pageIndex = page;
                    $.LoadingOverlay("show");
                    $.post(linkApi, data, function (resp) {
                        if (resp != null) {
                            $("#" + tagAppend + " tbody").html("");
                            $("#" + tagAppend + " tbody").html(resp);
                            if (typeof $(".page-home").val() != "undefined") {
                                $(".page-home").show();
                            } else {
                                var html = '<div class="page-home lo-paging"><div class="col-xs-12 col-md-4 form-inline"><label>Hiển thị <select class="ddlpage form-control"><option value="2">2</option><option value="20">20</option><option value="50">50</option><option value="100">100</option><option value="150">150</option><option value="200">200</option></select> tin</label></div><div class="col-xs-12 col-md-8 lo-paging-0"><div class="dataTables_paginate homepagging "><div class="pagination" id="pagination"></div></div></div></div>';
                                $(".pagecus").append(html);
                            }
                            $.LoadingOverlay("hide");
                        }
                    });
                }
            }
        });
    },

    fis_vnp_js_common.prototype.notifyNohide = function(text, style) {
        var icon = 'fa fa-adjust';
        if(style == "error"){
            icon = "fa fa-exclamation";
        }else if(style == "warning"){
            icon = "fa fa-warning";
        }else if(style == "success"){
            icon = "fa fa-check";
        }else if(style == "info"){
            icon = "fa fa-question";
        }else{
            icon = "fa fa-adjust";
        }
        $.notify({
            title: 'Thông báo',
            text: text,
            image: "<i class='"+icon+"'></i>"
        }, {
            style: 'metro',
            className: style,
            globalPosition: 'bottom right',
            showAnimation: "show",
            showDuration: 0,
            hideDuration: 0,
            autoHide: false,
            clickToHide: true
        });
    },

    fis_vnp_js_common.prototype.notifyAutoHideNotify = function (text, style) {
        var icon = "fa fa-adjust";
        if(style == "error"){
            icon = "fa fa-exclamation";
        }else if(style == "warning"){
            icon = "fa fa-warning";
        }else if(style == "success"){
            icon = "fa fa-check";
        }else if(style == "info"){
            icon = "fa fa-question";
        }else{
            icon = "fa fa-adjust";
        }
        $.notify({
            title: 'Thông báo',
            text: text,
            image: "<i class='"+icon+"'></i>"
        }, {
            style: 'metro',
            className: style,
            globalPosition: 'bottom right',
            showAnimation: "show",
            showDuration: 0,
            hideDuration: 0,
            autoHideDelay: 5000,
            autoHide: true,
            clickToHide: true
        });
    },

    fis_vnp_js_common.prototype.loadingPanel = function () {
        var $portlet = $('.portlet');
        $portlet.append('<div class="panel-disabled"><div class="lds-roller"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>');
    },

    fis_vnp_js_common.prototype.endLoadingPanel = function () {
        var $portlet = $('.portlet');
        var $pd = $portlet.find('.panel-disabled');
        $pd.remove();
    }

    return {
        fis_vnp: function () {
            var fis_vnp = new fis_vnp_js_common();
            return fis_vnp;
        }
    };
})();

$(document).ajaxError(function() {
    $.LoadingOverlay("hide");
    fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống đăng gặp sự cố hoặc bạn không có quyền thao tác dữ liệu. Vui lòng liên hệ với quản trị viên để biết thêm chi tiết.<br> Xin cảm ơn!!!", "error");
});