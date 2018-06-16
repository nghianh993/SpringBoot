$(document).ready(function () {
    $.validator.addMethod("valueNotEquals", function (value, element, arg) {
        return arg !== value;
    }, "Giá trị không hợp lệ");

    $('#permissionTree').nestable({
        maxDepth: 0,
        noDragClass:'dd-nodrag',
        handleClass: 'handleClass'
    });

    $(document).on("click", "#btnAddPermission", function () {
        var _this = $(this);
        var id = _this.attr("data-id");
        loadGoup();
        $("#permissionModal .modal-title").html('<i class="fa fa-check-square-o"></i> Thêm mới chức năng')
        $(".btnModal").addClass("btnaddper");
        $(".btnModal").removeClass("btnupdateper");
        $(".btnModal").html('<i class="fa fa-check-square-o"></i> Thêm mới');

        $("#txtname").val("");
        $("#txtcode").val("");
        $("#txtlink").val("");
        $(".slgrper").val(0);

        $("#permissionModal").modal("show");
    });

    $(document).on("click", "#btnAddGroup, .btnaddgroup", function (e) {
        e.preventDefault();
        loadGoup();
        $("#permissionModal").modal("hide");
        if($(this).hasClass('btnaddgroup'))
            $(".btnCloseGroupModal").addClass('showmodal');
        else
            $(".btnCloseGroupModal").removeClass('showmodal');
        $(".btnGroupModal").removeClass("btn_update");
        $(".btnGroupModal").addClass("btn_add");
        $("#frmFormAddGroup").validate().resetForm();
        $(".btnGroupModal").html('<i class="fa fa-check-square-o"></i> Thêm mới');
        $('#goupModal .modal-title').html('<i class="fa fa-check-square-o"></i> Thêm mới nhóm chức năng');
        $('#txtnamegroup').val('');
        $("#goupModal").modal("show");
    });

    $(document).on('click', '.btnCloseGroupModal', function () {
        if($(this).hasClass('showmodal')){
            loadGoup();
            $("#permissionModal").modal("show");
        }
    });

    $(document).on("click", ".btnGroupModal", function () {
        $("#frmFormAddGroup").submit();
    });

    $(document).on("click", ".btnaddper, .btnupdateper", function () {
        $("#frmFormPermission").submit();
    });

    $("#frmFormPermission").validate({
        rules: {
            txtname: {
                required: true
            },
            txtlink: {
                required: true
            },
            slgrper: {
                valueNotEquals: "0"
            }
        },
        messages: {
            txtname: {
                required: "Tên không được để trống"
            },
            txtlink: {
                required: "Đường dẫn không được để trống"
            },
            slgrper: {
                valueNotEquals: "Chọn một nhóm chức năng"
            }
        },
        submitHandler: function () {
            var data = {
                description: $.trim($("#txtname").val()),
                islock: $(".chklock").prop("checked"),
                parentId: $(".slgrper").val(),
                link: $("#txtlink").val()
            }
            $.LoadingOverlay("show");
            if ($(".btnModal").hasClass("btnaddper")) {
                $.post(root + "/api/admin/permission/add", data, function (resp) {
                    $.LoadingOverlay("hide");
                    if (resp.result) {
                        if (resp.code == 1) {
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Thêm mới chức năng thành công", "success");
                            $("#permissionModal").modal("hide");
                            setTimeout(function () {
                                location.reload();
                            }, timeoutTime);

                        } else {
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Không thêm được chức năng vào nhóm chức năng !!!", "error");
                        }
                    } else {
                        fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm mới dữ liệu. <br>Vui lòng thao tác lại !!!", "error");
                    }
                });
            } else {
                if ($(".btnModal").hasClass("btnupdateper")) {
                    data.id = parseInt($(".btnModal").attr("data-id"));
                    $.post(root + "/api/admin/permission/edit", data, function (resp) {
                        $.LoadingOverlay("hide");
                        if (resp.result) {
                            if (resp.code == 1) {
                                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Cập nhật chức năng thành công", "success");
                                $("#permissionModal").modal("hide");
                                setTimeout(function () {
                                    location.reload();
                                }, timeoutTime);
                            } else {
                                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Không cập nhật được thông tin chức năng!!!", "error");
                            }
                        } else {
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm cập nhật dữ liệu.  <br>Vui lòng thao tác lại !!!", "error");
                        }
                    });
                }
            }
        }
    })

    $("#frmFormAddGroup").validate({
        rules: {
            txtnamegroup: {
                required: true
            }
        },
        messages: {
            txtnamegroup: {
                required: "Tên nhóm chức năng không được để trống"
            }
        },
        submitHandler: function () {
            var data = {
                name: $.trim($("#txtnamegroup").val()),
                parentId: $(".slgroupadd").val()
            };
            $.LoadingOverlay("show");
            if ($(".btnGroupModal").hasClass("btn_add")) {
                $.post(root + "/api/admin/permission/addgroup", data, function (resp) {
                    $.LoadingOverlay("hide");
                    if (resp.result) {
                        fis_vnp_js.fis_vnp().notifyAutoHideNotify("Thêm mới nhóm chức năng thành công", "success");
                        $("#goupModal").modal("hide");
                        if($(".btnCloseGroupModal").hasClass('showmodal')){
                            loadGoup();
                            $("#permissionModal").modal("show");
                        }else {
                            setTimeout(function () {
                                location.reload();
                            }, 2000);
                        }
                    } else {
                        fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm mới dữ liệu. Vui lòng thao tác lại!!!", "error");
                    }
                });
            } else {
                if ($(".btnGroupModal").hasClass("btn_update")) {
                    data.id = parseInt($(".btnGroupModal").attr("data-id"));
                    $.post(root + "/api/admin/permission/updategroup", data, function (resp) {
                        $.LoadingOverlay("hide");
                        if (resp.result) {
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Cập nhật thông tin nhóm chức năng thành công", "success");
                            $("#goupModal").modal("hide");
                            setTimeout(function () {
                                location.reload();
                            }, timeoutTime);
                        } else {
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm mới dữ liệu. Vui lòng thao tác lại!!!", "error");
                        }
                    });
                }
            }
        }
    });

    $(document).on("click", ".btneditper", function () {
        var _this = $(this);
        var id = _this.attr("data-id");

        loadGoup();
        $.LoadingOverlay("show");
        var data = {
            id: id
        }
        $.post(root + "/api/admin/permission/detail", data, function (resp) {
            $.LoadingOverlay("hide");
            if (resp.result) {
                $("#txtname").val(resp.resultData.description);
                $("#txtcode").val(resp.resultData.code);
                $("#txtlink").val(resp.resultData.link);
                setTimeout(function () {
                    $(".slgrper").val(resp.resultData.parentId);
                }, 500);
                $("#slmethod").val(resp.resultData.methods).trigger("chosen:updated");

                $("#permissionModal .modal-title").html('<i class="fa fa-check-square-o"></i> Cập nhật chức năng');
                $(".btnModal").attr("data-id", id);
                $(".btnModal").html('<i class="fa fa-check-square-o"></i> Cập nhật');
                $(".btnModal").removeClass("btnaddper");
                $(".btnModal").addClass("btnupdateper");
                $("#frmFormPermission").validate().resetForm();
                $("#permissionModal").modal("show");
            } else {
                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm lấy dữ liệu. Vui lòng thao tác lại!!!", "error");
            }
        });
    });

    $(document).on("click", ".btndelPer", function () {
        var _this = $(this);
        var id = _this.attr("data-id");
        var data = {
            id: id
        };
        swal({
            title: "Thông báo",
            text: "Bạn có chắc muốn khóa chức năng này không?",
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
                fis_vnp_js.fis_vnp().postDataAndReloadPage(root + "/api/admin/permission/delete", data, "Xóa chức năng thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $(document).on("click", ".btnlockper", function () {
        var _this = $(this);
        var id = _this.attr("data-id");
        var data = {
            id: id
        };
        swal({
            title: "Thông báo",
            text: "Bạn có chắc muốn khóa chức năng này không?",
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
                _this.removeClass("btnlockper");
                _this.addClass("btnunlockper");
                fis_vnp_js.fis_vnp().postDataAndReloadPage(root + "/api/admin/permission/lock", data, "Khóa chức năng thành công", "Hệ thống gặp sự cố khi thao tác dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $(document).on('click', '.btndeletegroup', function(){
        var _this = $(this);
        var id = _this.attr("data-id");
        var data = {
            id: id
        };
        swal({
            title: "Thông báo",
            text: "Bạn có muốn xoá nhóm chức năng và toàn bộ chức năng trong nhóm này không?",
            dangerMode: true,
            type: "warning",
            buttons: {
                cancel: {
                    text: "Đóng",
                    visible: true,
                },
                confirm: {
                    text: "Đồng ý",
                },
            },
        }).then((isConfirm) => {
            if (isConfirm) {
                fis_vnp_js.fis_vnp().postDataAndReloadPage(root + "/api/admin/permission/deletegroup", data, "Xoá nhóm chức năng thành công", "Hệ thống gặp sự cố khi thao tác dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $(document).on("click", ".btnunlockper", function () {
        var _this = $(this);
        var id = _this.attr("data-id");
        var data = {
            id: id
        };
        swal({
            title: "Thông báo",
            text: "Bạn có chắc muốn mở khóa chức năng này không?",
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
                _this.removeClass("btnunlockper");
                _this.addClass("btnlockper");
                fis_vnp_js.fis_vnp().postDataAndReloadPage(root + "/api/admin/permission/unlock", data, "Mở khóa chức năng thành công", "Hệ thống gặp sự cố khi thao tác dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $(document).on("click", ".btneditgoup", function () {
        var _this = $(this);
        var id = _this.attr("data-id");
        loadGoup();
        $.LoadingOverlay("show");
        var data = {
            id: id
        }
        $.post(root + "/api/admin/permission/groupdetail", data, function (resp) {
            $.LoadingOverlay("hide");
            if (resp.result) {
                if (resp.result.resultData !== null) {
                    $("#txtnamegroup").val(resp.resultData.name);
                    setTimeout(function () {
                        $(".slgroupadd").val(resp.resultData.parentId);
                    }, 500);
                    $("#goupModal .modal-title").html('<i class="fa fa-check-square-o"></i> Cập nhật nhóm chức năng');
                    $(".btnGroupModal").addClass("btn_update");
                    $(".btnGroupModal").attr("data-id", id);
                    $(".btnGroupModal").removeClass("btn_add");
                    $("#frmFormAddGroup").validate().resetForm();
                    $(".btnGroupModal").html('<i class="fa fa-check-square-o"></i> Cập nhật');
                    $("#goupModal").modal("show");
                } else {
                    fis_vnp_js.fis_vnp().notifyAutoHideNotify("Không tìm thấy dữ liệu bạn yêu cầu. Vui lòng thao tác lại!!!", "error");
                }
            } else {
                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm lấy dữ liệu. Vui lòng thao tác lại!!!", "error");
            }
        });
    });

    /*Help method*/
    function loadGoup() {
        $.LoadingOverlay("show");
        $.post(root + "/api/admin/permission/loadgroup", function (resp) {
            $.LoadingOverlay("hide");
            $(".slgroup").empty();
            $(".slgroup").html($.trim(resp));
        });
    };
});