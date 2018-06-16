$(document).ready(function() {
    jQuery.validator.addMethod("rgphone", function (value, element) {
        return this.optional(element) || /^(098|095|097|096|0169|0168|0167|0166|0165|0164|0163|0162|090|093|0122|0126|0128|0121|0120|091|094|0123|0124|0125|0127|0129|092|0188|0186|099|0199|086|088|089|087)[0-9]{7}$/.test(value);
    }, "Số điện thoại không đúng định dạng");
    $.validator.addMethod("valueNotEquals", function (value, element, arg) {
        return arg !== value;
    }, "Giá trị không hợp lệ");
    $.validator.setDefaults({
        ignore: ":hidden:not(.chosen-select)"
    });
    $('#txtbirthday').datepicker();
    var totalpage = parseInt($('#datatable').attr("data-total"));
    var totalrecord = parseInt($('#datatable').attr("data-totalrecord"));

    $('.date-picker').datepicker({
        autoclose: true,
        todayHighlight: true
    });

    $(document).on('change', '#islock', function () {
        if($(this).prop('checked'))
            $('.lockregion').show();
        else
            $('.lockregion').hide();
    });

    $(document).on('change', '.ddlpage', function () {
        reloaddata();
    });

    $('#slorganization, #slroll').chosen({
        disable_search: false,
        width: '100%',
        include_group_label_in_selected:true,
        search_contains: true,
        enable_split_word_search: true,
        no_results_text: "Không có dữ liệu với từ khoá"
    });

    $('#slrole').chosen({
        disable_search: false,
        width: '100%',
        placeholder_text_multiple : 'Nhóm quyền',
        include_group_label_in_selected:true,
        search_contains: true,
        enable_split_word_search: true,
        no_results_text: "Không có dữ liệu với từ khoá"
    });

    $('#slgroup').chosen({
        disable_search: false,
        width: '100%',
        placeholder_text_multiple : 'Cơ quan',
        include_group_label_in_selected:true,
        search_contains: true,
        enable_split_word_search: true,
        no_results_text: "Không có dữ liệu với từ khoá"
    });

    $(document).on('click', '#btnsearch', function () {
        reloaddata();
    });

    $(document).on('keyup', '#txtsearchbox', function () {
        reloaddata();
    });

    $(document).on('click', '.btndelete', function () {
        if (!$(this).hasClass("disabled")) {
            var selected = [];
            $('.checkboxItem:checked').each(function () {
                selected.push(parseInt($(this).attr('data-id')));
            });
            if (selected.length == 0) {
                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hãy chọn danh sách tài khoản cần xoá", "error");
            } else {
                swal({
                    title: "Thông báo",
                    text: "Bạn có chắc muốn xoá những tài khoản này không?",
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
                }).then((isConfirm) => {
                    if (isConfirm) {
                        fis_vnp_js.fis_vnp().postDataAndReload(root + "/api/admin/users/delete", {ids: selected.toString()}, reloaddata, "Xóa thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");

                    }
                });
            }
        }
    });

    $(document).on('click', '.btnlockall', function () {
        if (!$(this).hasClass("disabled")) {
            var selected = [];
            $('.checkboxItem:checked').each(function () {
                selected.push(parseInt($(this).attr('data-id')));
            });
            if (selected.length == 0) {
                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hãy chọn danh sách tài khoản cần khoá", "error");
            } else {
                swal({
                    title: "Thông báo",
                    text: "Bạn có chắc muốn khoá những tài khoản này không?",
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
                }).then((isConfirm) => {
                    if (isConfirm) {
                        fis_vnp_js.fis_vnp().postDataAndReload(root + "/api/admin/users/lockuser", {ids: selected.toString()},reloaddata, "Khoá tài khoản thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");
                    }
                });
            }
        }
    });

    $(document).on('click', '.btnunlockall', function () {
        if (!$(this).hasClass("disabled")) {
            var selected = [];
            $('.checkboxItem:checked').each(function () {
                selected.push(parseInt($(this).attr('data-id')));
            });
            if (selected.length == 0) {
                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hãy chọn danh sách tài khoản cần mở khoá", "error");
            } else {
                swal({
                    title: "Thông báo",
                    text: "Bạn có chắc muốn mở khoá những tài khoản này không?",
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
                }).then((isConfirm) => {
                    if (isConfirm) {
                        fis_vnp_js.fis_vnp().postDataAndReload(root + "/api/admin/users/unlockuser", {ids: selected.toString()}, reloaddata, "Mở khoá tài khoản thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");
                    }
                });
            }
        }
    });

    $(document).on('click', '.btnlock', function () {
        var _this = $(this);
        var id = _this.attr('data-id');
        var ids = new Array();
        ids.push(id);
        swal({
            title: "Thông báo",
            text: "Bạn có chắc muốn khoá tài khoản này không?",
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
        }).then((isConfirm) => {
            if (isConfirm) {
                _this.find('i').removeClass('fa-ban');
                _this.find('i').addClass('fa-unlock');
                _this.removeClass('btnlock');
                _this.addClass('btnunlock');
                fis_vnp_js.fis_vnp().postDataAndReload(root + "/api/admin/users/lockuser", {ids: ids.toString()}, reloaddata, "Khoá tài khoản thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $(document).on('click', '.btnunlock', function () {
        var _this = $(this);
        var id = _this.attr('data-id');
        var ids = new Array();
        ids.push(id);
        swal({
            title: "Thông báo",
            text: "Bạn có chắc muốn mở khoá tài khoản này không?",
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
        }).then((isConfirm) => {
            if (isConfirm) {
                _this.find('i').removeClass('fa-unlock');
                _this.find('i').addClass('fa-ban');
                _this.removeClass('btnunlock');
                _this.addClass('btnlock');
                fis_vnp_js.fis_vnp().postDataAndReload(root + "/api/admin/users/unlockuser", {ids: ids.toString()}, reloaddata, "Mở khoá tài khoản thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $('.btnnew').click(function(){
        $('.btnsubmit').addClass('btnadd');
        $('.btnsubmit').removeClass('btnupdate');
        $('.btnsubmit').html('<i class="fa fa-check-square-o"></i> Thêm mới');
        $('#userModal .modal-title').html('<i class="fa fa-check-square-o"></i> Thêm mới tài khoản');
        $("#txtemail").val('');
        $('#txtfullname').val('');
        $('#txtbirthday').val('');
        $('#txtphone').val('');
        $('#txtaddress').val('');
        $('#slrole').val('').trigger("chosen:updated");
        $('.slgroup').val('');
        $('#txtemail').removeAttr('disabled');
        $('#userModal').modal('show');
    });

    $(document).on('click', '.btnsubmit', function() {
        $("#frmForm").submit();
    });

    $("#frmForm").validate({
        rules: {
            txtusername: {
                required: true,
                minlength: 6,
                maxlength: 50,
                remote: {
                    url: root + "/api/admin/users/checkusername",
                    type: "post",
                    data:
                        {
                            username: function()
                            {
                                return $("#txtusername").val();
                            }
                        }
                }
            },
            txtfullname: {
                required: true,
                minlength: 2,
            },
            txtemail : {
                required: true,
                email: true
            },
            txtpassword: {
                required: true,
                minlength: 6,
                maxlength: 100
            },
            txtrepassword: {
                required: true,
                minlength: 6,
                maxlength: 100,
                equalTo: '#txtpassword'
            },
            txtbirthday: {
                required: true
            },
            txtphone: {
                required: true,
                rgphone: true
            },
            txtaddress: {
                required: true
            },
            slrole: {
                required: true
            },
            lockregion: {
                required: $('#islock').is(':checked')
            },
            slorganization: {
                required: true
            }
        },
        messages: {
            txtusername: {
                required: 'Tên đăng nhập không được để trống',
                minlength: 'Tên đăng nhập có ít nhất 6 kí tự',
                maxlength: 'Tên đăng nhập có ít hơn 50 kí tự',
                remote: 'Tên đăng nhập đã tồn tại trong hệ thống'
            },
            txtfullname: {
                required: 'Họ tên không được để trống',
                minlength: 'Họ tên có ít nhất 2 ký tự',
            },
            txtemail : {
                required: 'Email không được để trống',
                email: 'Email không đúng định dạng'
            },
            txtpassword: {
                required: 'Mật khẩu không được để trống',
                minlength: 'Mật khẩu không được nhỏ hơn 6 kí tự',
                maxlength: 'Mật khẩu không được vượt quá 100 kí tự'
            },
            txtrepassword: {
                required: 'Mật khẩu không được để trống',
                minlength: 'Mật khẩu không được nhỏ hơn 6 kí tự',
                maxlength: 'Mật khẩu không được vượt quá 100 kí tự',
                equalTo: 'Mật khẩu không khớp'
            },
            txtbirthday: {
                required: 'Ngày sinh không được để trống'
            },
            txtphone: {
                required: 'Số điện thoại không được để trống',
                rgphone : 'Số điện thoại không đúng định dạng'
            },
            txtaddress: {
                required: 'Địa chỉ không được để trống'
            },
            slrole: {
                required: 'Vui lòng chọn nhóm quyền của người dùng'
            },
            lockregion: {
                required: 'Vui lòng nhập vào lý do khoá tài khoản'
            },
            slorganization: {
                required : 'Vui lòng chọn phòng ban'
            }
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") == "slrole" )
                error.insertAfter(".chosen-container");
            else
                if(element.attr("name") == "txtbirthday")
                    error.insertAfter(".input-group");
                else
                    error.insertAfter(element);
        },
        submitHandler : function() {
            var data  = {
                username : $.trim($("#txtusername").val()),
                email: $.trim($("#txtemail").val()),
                fullname: $('#txtfullname').val(),
                password: $("#txtpassword").val(),
                dob: $('#txtbirthday').val(),
                phone: $('#txtphone').val(),
                address: $('#txtaddress').val(),
                lstRole: $('#slrole').val().toString(),
                organizationid: $('#slorganization').val()
            }
            $.LoadingOverlay("show");
            if($(".btnsubmit").hasClass("btnadd")){
                $.post(root + "/api/admin/users/add", data, function(resp) {
                    $.LoadingOverlay("hide");
                    if(resp.result){
                        fis_vnp_js.fis_vnp().notifyAutoHideNotify("Thêm mới dữ liệu thành công", "success");
                        $("#userModal").modal("hide");
                        reloaddata();
                    }else{
                        fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi thêm mới dữ liệu. <br>Vui lòng thao tác lại!!!", "error");
                    }
                });
            }else{
                if($(".btnsubmit").hasClass("btnupdate")){
                    data.id = parseInt($(".btnsubmit").attr("data-id"));
                    $.post(root + "/api/admin/users/edit", data, function(resp) {
                        $.LoadingOverlay("hide");
                        if(resp.result){
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Cập nhật thông tin thành công", "success");
                            $("#userModal").modal("hide");
                            reloaddata();
                        }else{
                            fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi cập nhật dữ liệu. <br>Vui lòng thao tác lại!!!", "error");
                        }
                    });
                }
            }
        }
    });

    $(document).on('click', '.btndel', function() {
        var _this = $(this);
        var id = _this.attr('data-id');
        var ids = new Array();
        ids.push(id);
        swal({
            title: "Thông báo",
            text: "Bạn có chắc muốn xoá tài khoản này không?",
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
        }).then((isConfirm) => {
            if (isConfirm) {
                fis_vnp_js.fis_vnp().postDataAndReload(root + "/api/admin/users/delete", {ids: ids.toString()}, reloaddata, "Xóa thành công", "Hệ thống gặp sự cố khi xóa dữ liệu. <br>Vui lòng thao tác lại!!!");
            }
        });
    });

    $(document).on('click', '.btnedit', function() {
        var _this = $(this);
        var id = _this.attr('data-id');
        $.LoadingOverlay('show');
        $.post(root + '/api/admin/users/getdetail', {id : id}, function(resp) {
            $.LoadingOverlay('hide');
            if(resp.result){
                $('.btnsubmit').removeClass('btnadd');
                $('.btnsubmit').addClass('btnupdate');
                $('.btnsubmit').attr('data-id', id);
                $('.btnsubmit').html('<i class="fa fa-check-square-o"></i> Cập nhật');
                $('#userModal .modal-title').html('<i class="fa fa-check-square-o"></i> Cập nhật tài khoản');
                $('#txtpassword').attr('disabled', 'disabled');
                $('#txtrepassword').attr('disabled', 'disabled');
                $('#txtusername').attr('disabled', 'disabled');
                $("#txtemail").val(resp.resultData.email);
                $('#txtfullname').val(resp.resultData.fullname);
                $('#txtbirthday').val(resp.resultData.dob);
                $('#txtpassword').val('**********');
                $('#txtrepassword').val('**********');
                $('#txtusername').val(resp.resultData.username);
                $('#txtphone').val(resp.resultData.phone);
                $('#txtaddress').val(resp.resultData.address);
                $('#slrole').val(resp.resultData.lstRole).trigger("chosen:updated");
                $('#slgroup').val(resp.resultData.organizationid).trigger("chosen:updated");
                $("#userModal").modal("show");
            }
            else{
                fis_vnp_js.fis_vnp().notifyAutoHideNotify("Hệ thống gặp sự cố khi cập nhật dữ liệu. <br>Vui lòng thao tác lại!!!", "error");
            }
        });
    });

    //select/deselect a row when the checkbox is checked/unchecked
    $('#datatable-editable').on('click', 'td input[type=checkbox]' , function(){
        var $row = $(this).closest('tr');
        if($row.is('.detail-row ')) return;
        if(this.checked) $row.addClass(active_class);
        else $row.removeClass(active_class);
    });

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
                    fis_vnp_js.fis_vnp().loadDataAll(root + "/api/admin/users/loaddata", "datatable-editable", data);
                }
            }
        });
    }

    function reloaddata() {
        if (typeof $(".ddlpage").val() != "undefined") {
            pageSize = parseInt($(".ddlpage").val());
        }
        var data = {
            pageIndex: 1,
            pageSize: pageSize,
            key : $.trim($("#txtsearchbox").val())
        };
        fis_vnp_js.fis_vnp().loadDataAndPagingAll(root + "/api/admin/users/loaddata", "datatable-editable", data);
    }
});