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

//$("#download").click(function (event) {
//    debugger;
//    event.preventDefault();
//    var pid = $(this).parent().attr("id");
//    $.ajax({
//        type: "GET",
//        url: "downloadPainting",
//        data: {
//            painting_id : pid
//        },
//        success: function (data) {
//            Materialize.toast(data.message, 3000);
//        },
//        error: function (e) {
//            Materialize.toast("Error", 3000);
//        },
//        done: function (e) {
//            alert(e);
//        }
//
//    });
//});


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
                                                <p class="left">Size-&nbsp;&nbsp;&nbsp;'+ obj.sze +' px</p><br>\n\
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



$('#dropdown2 li').on('click', function() {
    var liValue = $(this).html();
     $.ajax({
        type: "GET",
        url: "sortPaint",
        data: {
            criteria: liValue
        },
        success: function (data) {
            if (data.status === "successfull") {
                $("#cart_parent").empty();
                $("#headings").text("Paintings by "+ liValue);
                showSortPainting(data);
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

function showSortPainting(data) {
    
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
                                                <p class="left">Size-&nbsp;&nbsp;&nbsp;'+ obj.sze +' px</p><br>\n\
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


    $("#forgot").click(function (event) {
        event.preventDefault();
        $('#modal1').modal('close');
        $('#modal3').modal('open');

    });

    $("#forgotSubmit").click(function (event) {
        event.preventDefault();
        var mail = $("#mail").val();
        $.ajax({
            type: "POST",
            url: "forgotMail",
            data: {
                mail: mail
            },
            success: function (data) {
                if (data.message === "User does not exist" || data.message === "Email id cannot be null") {
                    Materialize.toast(data.message,3000);                
                } else{
                   $("#gParent").empty(); 
                   $('#gParent').append('<div class="input-field"><input type="text" id="code" name="code">\n\
                                            <label for="code">Enter the 6 digit code</label>\n\
                                        </div>\n\
                                        <input type="hidden" id="fstatus" value="'+data.status+'">\n\
                                        <input type="hidden" id="femail" value="'+mail+'">\n\
                                        <button id="csubmit" class="center col s12 btn waves-effect waves-light light-blue darken-1 z-depth-2" onClick="showforget();" > Submit \n\
                                        </button>\n\
                                       ');               
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

    function showforget() {
        var code1 = $("#code").val();
        var code2 = $("#fstatus:hidden").val();
        var email = $("#femail:hidden").val();
        if(code1 === code2){
            $("#gParent").empty(); 
            $('#gParent').append('<div class="input-field"><input type="password" id="passw" name="passw">\n\
                                    <label for="passw">Create new password</label>\n\
                                  </div>\n\
                                    <button id="NewPassSubmit" class="center col s12 btn waves-effect waves-light light-blue darken-1 z-depth-2" onClick="setPass();">Submit</button>\n\
                                    <input type="hidden" id="getemail" value="'+email+'">'); 
        } else {
            Materialize.toast("Wrong code,try again",3000);
        }

    }

    function setPass() {   
        
        event.preventDefault();
        var pass = $("#passw").val();
        var email =$("#getemail:hidden").val();
        
        $.ajax({
            type: "POST",
            url: "setNewPass",
            data: {
                pass: pass,
                email: email
            },
            success: function (data) {
                Materialize.toast(data.status,3000);
                $('#modal3').modal('close');
            },
            error: function (e) {
                alert("ERROR: "+e);
            },
            done: function (e) {
                alert("DONE");
            }
        });

    }
