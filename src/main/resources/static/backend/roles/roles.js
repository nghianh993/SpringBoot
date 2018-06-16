$(document).ready(function() {
    $.validator.setDefaults({ ignore: ":hidden:not(.chosen-select)" });
    $('#slper').chosen({
        disable_search: false,
        width: '100%',
        placeholder_text_multiple : 'Chọn quyền',
        include_group_label_in_selected:true,
        search_contains: true,
        enable_split_word_search: true,
        no_results_text: "Không có dữ liệu!"
    });

    var totalpage = parseInt($('#datatable').attr("data-total"));
    var totalrecord = parseInt($('#datatable').attr("data-totalrecord"));

    if (totalpage > 1) {
        var obj = $('#pagination').twbsPagination({
            totalPages: totalpage,
            visiblePages: 3,
            first: 'Trang đầu',
            prev: 'Trước',
            next: 'Tiếp',
            last: 'Trang cuối',
            onPageClick: function (event, page) {
                var curentpage = parseInt($('#datatable').attr("data-page"));
                if (curentpage != page) {
                    if (typeof $(".ddlpage").val() != "undefined") {
                        pageSize = parseInt($(".ddlpage").val());
                    }
                    var data = {
                        pageIndex: page,
                        pageSize: pageSize,
                        key : $.trim($("#txtsearchbox").val())
                    };
                    fis_vnp_js.fis_vnp().loadDataAll(root + "/api/admin/roles/loaddata", "datatable-editable", data);
                }
            }
        });
    };

    $(document).on('click', '#btnsearchrole', function () {
        loadData();
    });

    $(document).on('keyup', '#txtsearchbox', function () {
        loadData();
    });

    $(document).on("click", ".btnedit", function() {
        var _this = $(this);
        var id = _this.attr("data-id");
        $.LoadingOverlay("show");
        var data = {
            id: id
        }
        $.post(root + "/api/admin/roles/detail", data, function(resp){
            $.LoadingOverlay("hide");
            if(resp.result){
                $("#txtrolename").val(resp.resultData.name);
                //$('#sluser').val(resp.resultData.lstuser).trigger("chosen:updated");
                $("#slper").val(resp.resultData.lstPermision).trigger("chosen:updated");
                $("#GroupModal .modal-title").html('<i class="fa fa-check-square-o"></i> Cập nhật nhóm người dùng')
                $(".btnModal").attr("data-id", id);
                $("#frmForm").validate().resetForm();
                $(".btnModal").html('<i class="fa fa-check-square-o"></i> Cập nhật');
                $(".btnModal").removeClass("btnadd");
                $(".btnModal").addClass("btnupdate");
                $("#GroupModal").modal("show");
            }else{
                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm lấy dữ liệu. <br>Vui lòng thao tác lại!!!", "error");
            }
        });
    });

    $(document).on("click", ".btnaddnew", function() {
        $(".btnModal").addClass("btnadd");
        $(".btnModal").removeClass("btnupdate");
        $(".btnModal").html('<i class="fa fa-check-square-o"></i> Thêm mới');
        $("#GroupModal .modal-title").html('<i class="fa fa-check-square-o"></i> Thêm mới nhóm người dùng');
        $("#txtrolename").val("");
        //$('#sluser').val(0).trigger("chosen:updated");
        $("#slper").val(0).trigger("chosen:updated");
        $("#GroupModal").modal("show");
    });

    $(document).on("click", ".btndelete", function(){
        var _this = $(this);
        var id = _this.attr("data-id");
        swal({
            title: "Thông báo",
            text: "Bạn có chắc muốn xóa nhóm người dùng này không?",
            type: "warning",
            dangerMode: true,
            buttons: {
                cancel: {
                    text: "Đóng",
                    visible: true,
                },
                confirm: {
                    text: "Đồng ý",
                },
            },
            closeOnConfirm: false
        }).then((isConfirm) => {
            if (isConfirm) {
                fis_vnp_js.fis_vnp().postDataAndReloadPage(root + "/api/admin/roles/delete", {id:id}, "Xóa nhóm người dùng thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $(document).on("click", ".btnModal", function() {
        $("#frmForm").submit();
    });

    $("#frmForm").validate({
        rules: {
            txtrolename : {
                required: true
            },
            slper: {
                required: true
            },
        },
        messages: {
            txtrolename: {
                required: "Tên nhóm người dùng không được để trống"
            },
            slper: {
                required: "Chọn quyền hệ thống"
            },
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") == "slper" )
                error.insertAfter(".chosen-container");
            else
                error.insertAfter(element);
        },
        submitHandler : function() {
            var data  = {
                name: $.trim($("#txtrolename").val()),
                //lstuser: $("#sluser").val().toString(),
                lstPermision: $("#slper").val().toString()
            }
            $.LoadingOverlay("show");
            if($(".btnModal").hasClass("btnadd")){
                $.post(root + "/api/admin/roles/add", data, function(resp) {
                    $.LoadingOverlay("hide");
                    if(resp.result){
                        if(resp.code == 1){
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Thêm mới nhóm người dùng thành công", "success");
                            $("#GroupModal").modal("hide");
                            loadData();
                        }else{
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Không thêm được nhóm người dùng!!!", "error");
                        }
                    }else{
                        fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm mới dữ liệu. <br>Vui lòng thao tác lại!!!", "error");
                    }
                });
            }else{
                if($(".btnModal").hasClass("btnupdate")){
                    data.id = parseInt($(".btnModal").attr("data-id"));
                    $.post(root + "/api/admin/roles/edit", data, function(resp) {
                        $.LoadingOverlay("hide");
                        if(resp.result){
                            if(resp.code == 1){
                                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Cập nhật nhóm người dùng thành công. <br>Các tài khoản thuộc nhóm người dùng này cần phải đăng nhập lại để sử dụng các người dùng đã cập nhật", "success");
                                $("#GroupModal").modal("hide");
                                loadData();
                            }else{
                                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Không cập nhật được thông tin nhóm người dùng!!!", "error");
                            }
                        }else{
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm cập nhật dữ liệu. <br>Vui lòng thao tác lại!!!", "error");
                        }
                    });
                }
            }
        }
    });

    $(document).on('change', '.ddlpage', function () {
        loadData();
    });

    function loadData() {
        if (typeof $(".ddlpage").val() != "undefined") {
            pageSize = parseInt($(".ddlpage").val());
        }
        var data = {
            pageIndex: 1,
            pageSize: pageSize,
            key : $.trim($("#txtsearchbox").val())
        };
        fis_vnp_js.fis_vnp().loadDataAndPagingAll(root + "/api/admin/roles/loaddata", "datatable-editable", data);
    }
});