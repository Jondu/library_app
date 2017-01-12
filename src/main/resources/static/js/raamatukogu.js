function laenuta(param) {
    var title = jQuery(param).parent().parent().find(".book_title").html();
    var userId = jQuery(param).parent().prev("td").find("select").val();
    var book = {
        "book": {
            "title": title
        },
        "loanedTo": {
            "id": userId,
        }
    };
    var jsonbook = JSON.stringify(book);
    jQuery.ajax({
        type: "POST",
        data: jsonbook,
        contentType: "application/json;",
        url: location+"loan",
        success: function(data) {
            jQuery(param).parent().parent().remove();
            get_unavailable_books();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("Please select a user for the book");
        }
    });
}

function tagasta(param) {
    var title = jQuery(param).parent().parent().find(".book_title").html();
    var userId = jQuery(param).parent().parent().find(".book_user").data("userid");
    var book = {
        "book": {
            "title": title
        },
        "loanedTo": {
            "id": userId,
        }
    };
    var jsonbook = JSON.stringify(book);
    jQuery.ajax({
        type: "POST",
        data: jsonbook,
        contentType: "application/json;",
        url: location+"return",
        success: function(data) {
            jQuery(param).parent().parent().remove();
            get_available_books();
        }
    });
}

function getPeople() {
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: location+"people",
        success: function(data) {
            jQuery(".available_people_list").empty();
            jQuery(".available_people_list").append("<option class='start_person_choice'>Choose a name</option>");
            $.each(data, function(key, data_item) {
                jQuery(".available_people_list").append("<option value=" + data_item.id + ">" + data_item.name + "</option>");
            })
        }
    });
}

function get_unavailable_books() {
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: location+"unavailable",
        success: function(data) {
            jQuery(".unavailable_book_table tbody").empty();
            $.each(data, function(key, data_item) {
                jQuery(".unavailable_book_table tbody").append("<tr><td class='book_title'>" + data_item.book.title +
                "</td><td class='book_user' data-userid=" + data_item.loanedTo.id + ">" + data_item.loanedTo.name +
                "</td><td>" + data_item.loanedTo.phone +
                "</td><td><button onclick='tagasta(this)' class='laenuta btn btn-success'>Return</button></td></tr>");
            })
            getPeople();
        }
    });
}


function get_available_books() {
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: location+"books",
        success: function(data) {
            jQuery(".available_book_table tbody").empty();
            $.each(data, function(key, data_item) {
                jQuery(".available_book_table tbody").append("<tr><td class='book_title''>" + data_item.title +
                "</td><td><select class='available_people_list'><option class='start_person_choice'>Choose a name</option></select></td><td><button onclick='laenuta(this)' class='laenuta btn btn-warning'>Lend</button></td></tr>");
            });
            getPeople();
        }
    });

}

function add_user() {
    var name = jQuery("input[name='name']").val();
    var phone = jQuery("input[name='phone']").val();
    var user = {
        "name": name,
        "phone": phone
    }
    var jsonuser = JSON.stringify(user);
    jQuery.ajax({
        type: "POST",
        contentType: "application/json;",
        data: jsonuser,
        url: location+"adduser",
        success: function(data) {
            getPeople();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("Please fill both fields!")
        }
    });

}

function add_book() {
    var title = jQuery("input[name='book_title']").val();
    var book = {
        "title": title
    }
    var jsonbook = JSON.stringify(book);
    jQuery.ajax({
        type: "POST",
        contentType: "application/json;",
        data: jsonbook,
        url: location+"addbook",
        success: function(data) {
            get_available_books();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        alert("Book must have a title!")
        }
    });

}

jQuery(document).ready(function() {

    jQuery(".add_user button").click(function() {
        add_user();
        jQuery("input[name='phone']").val('');
        jQuery("input[name='name']").val('');
    });

    jQuery(".add_book button").click(function() {
        add_book();
        jQuery("input[name='book_title']").val('');
    });

    get_unavailable_books();
    get_available_books();
});