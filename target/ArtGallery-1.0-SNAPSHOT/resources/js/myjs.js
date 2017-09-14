$(document).ready(function () {
    $('.carousel.carousel-slider').carousel({full_width: true});
    $(".dropdown-button").dropdown();
    $('.modal').modal();
    $('select').material_select();
    $("img").on("contextmenu",function(){
       return false;
    }); 
    $('.materialboxed').materialbox();
});


setInterval(function () {
    $('.carousel').carousel('next');
}, 3000);


$("#edit").click(function () {
    var num = document.getElementById("inv");
    num.style.display = "none";
    var num1 = document.getElementById("des");
    num1.style.display = "inline";
    var num2 = document.getElementById("contact");
    num2.disabled = false;
    var num3 = document.getElementById("upload");
    num3.style.display = "block";
    var num5 = document.getElementById("safe");
    num5.style.display = "inline";
    var num6 = document.getElementById("edit");
    num6.style.display = "none";

});

$("#safe").click(function (event) {
    document.getElementById("safe").style.display = "none";
    document.getElementById("upload").style.display = "none";
    document.getElementById("edit").style.display = "inline";
    event.preventDefault();
    saveAjax();
});

function saveAjax() {
    var desc = document.getElementById("des").value;
    var con = document.getElementById("contact").value;
    $.ajax({
        type: "POST",
        url: "saveDes",
        dataType: 'json',
        data: {
            description: desc,
            contact: con
        },
        success: function (data) {
            Materialize.toast(data.message, 4000);
            document.getElementById('des').style.display = "none";
            document.getElementById('inv').style.display = "inline";
            document.getElementById('inv').innerHTML = data.usr.description;
            document.getElementById('contact').value = data.usr.contact;
            document.getElementById('contact').disabled = "true";
            document.getElementById('inv').style.color = "#9e9e9e";
            document.getElementById('edit').style.display = "inline";
        },
        error: function (e) {
            Materialize.toast("Error occured", 4000);
        },
        done: function (e) {
            alert(e);
        }

    });

}

$("#upload").click(function (event) {
    event.preventDefault();
    $("#upl:hidden").trigger("click");

});

$("#upl:hidden").change(function () {
    uploadAjax();
});

function uploadAjax() {

    var form = $('#formId')[0];
    var data = new FormData(form);
    var img = document.getElementById("image");
    var save = document.getElementById("safe");
    var edit = document.getElementById("edit");
    var des = document.getElementById("des");
    var contact = document.getElementById("contact");
    $.ajax({

        type: "POST",
        enctype: 'multipart/form-data',
        url: "picUpload",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            img.src = data.usr.pic;
            Materialize.toast(data.message,4000);
            save.style.display = "none";
            edit.style.display = "inline";
            des.disabled = "true";
            contact.disabled = "true";
        },
        error: function (e) {
            alert("error");
        },
        done: function (e) {

        }
    });

    document.getElementById("upload").style.display = "none";
}

$("#uploadPaint").click(function (event) {
    event.preventDefault();
    document.getElementById("uploadPaint").style.display = "none";
    $("#uplp:hidden").trigger("click");
});

$("#uplp:hidden").change(function () {
    document.getElementById("uplp").style.display = "inline";
    if(document.getElementById("uplp").value !== "") {
        document.getElementById("savePaint").style.display = "inline";
    } else {
        document.getElementById("savePaint").style.display = "none";
    }
});


$("#cart_parent").on("click", "a.deleteViewRow", function () {

    var pid = $(this).parent().parent().parent().attr("id");
    var $this = this;
    $.ajax({
        type: "GET",
        url: "delPaint",
        data: {
            pid: pid
        },
        success: function (data) {
            if (data.message === "Successfully Removed") {
                $($this).parent().parent().parent().remove();
            }
            Materialize.toast(data.message, 4000);
        },
        error: function (e) {
            Materialize.toast("Error occured", 4000);
        },
        done: function (e) {
            alert(e);
        }

    });
});


$("#cart_parent").on("click", "a.crt", function (event) {
    
    event.preventDefault();
    var id = $(this).parent().attr("id");
    $.ajax({
        type: "GET",
        url: "addCart",
        data: {
            paint_id : id
        },
        success: function (data) {
            Materialize.toast(data.message, 4000);
        },
        error: function (e) {
            Materialize.toast("Error occured", 4000);
        },
        done: function (e) {
            alert(e);
        }

    });
});


$('#dropdown1 li').on('click', function() {
    var liVal = $(this).html();
    $.ajax({
        type: "GET",
        url: "selectPaint",
        data: {
            type: liVal
        },
        success: function (data) {
            if (data.status === "successfull") {
                $("#cart_parent").empty();
                $("#headings").text(liVal+" paintings");
                showPainting(data);
            }
        },
        error: function (e) {
            alert("ERROR: "+e);
        },
        done: function (e) {
            alert("DONE");
        }
    });
});

function showPainting(data) {
    
    if(data.msg !== "got") {
        $('#cart_parent').append('<center class="blue-text">'+data.msg+'</center>');
    } else {
        $.each(data.paintings, function (idx, obj) {
            $('#cart_parent').append('<div class="col s4">\n\
                                        <div class="card">\n\
                                            <div class="card-image">\n\
                                                <img src="' + obj.thumbnail_add + '" style="height: 200px">\n\
                                                <span class="card-title">Rs/- ' + obj.price + '</span>\n\
                                            </div>\n\
                                            <div class="card-content">\n\
                                                <p class="left">Title-&nbsp;&nbsp;&nbsp;' + obj.name + '</p><br>\n\
                                                <p class="left">Category-&nbsp;&nbsp;&nbsp;' + obj.type + '</p><br>\n\
                                                <p class="left">By-&nbsp;&nbsp;&nbsp;<a href="artistPaint?uid=' + obj.user_id + '">' + data.names[idx] + '</a></p>\n\
                                            </div>\n\
                                            <div class="card-action" id="' + obj.painting_id + '">\n\
                                                <a href="" class="crt"><i class="material-icons">add_shopping_cart</i>Add To Cart</a>\n\
                                            </div>\n\
                                        </div>\n\
                                     </div>');
        });
    }
}


$("#upload").click(function (event) {
    event.preventDefault();
    $('#modal1').modal('close');
    $('#modal3').modal('open');
    
});

//
//function showProductsAjax(){
//     $.ajax({
//        type: "GET",
//        url: "home",
//        data: {
//        },
//        success: function (data) {
//            if (data.status === "successfull") {
//                showProduct(data);
//            }
//        },
//        error: function (e) {
//            alert("ERROR: ", e);
//        },
//        done: function (e) {
//            alert("DONE",e);
//        }
//    }); 
//}
//
//function showProduct(data) {
//    $.each(data.plist, function (idx, obj) {
//        $('#products').append('<div class="col s3">\n\
//                                <div class="card" id="'+obj.product_id+'">\n\
//                                    <div class="card-image">\n\
//                      =                  <img src="http://lorempixel.com/500/500/nature">\n\
//                                            <a class="btn-floating btn-large halfway-fab waves-effect waves-light indigo darken-4">\n\
//                                            <i class="fa fa-shopping-cart" aria-hidden="true"></i></a>\n\
//                                    </div>\n\
//                                    <div class="card-content">\n\
//                                        <span class="card-title">'+obj.title+'</span>\n\
//                                        <p class="counter" style="font-size: 15px;"> By <a href="#"'+obj.artist_id+'</a></p>\n\
//                                        <p class="counter" style="font-size: 25px;">'+obj.price+'<span class="unit" style="font-size: 12px;"> RS</span>\n\
//                                        <p><a href="#">More Info</a></p>\n\
//                                    </div>\n\
//                                </div>\n\
//                            </div>');
//    });
//}
