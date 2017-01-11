function laenuta(param) {
    var title = jQuery(param).parent().parent().find(".book_title").html();
    console.log(title);
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
        url: "http://localhost:8080/raamatukogu/loan",
        success: function(data) {
            jQuery(param).parent().parent().remove();
            get_unavailable_books();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

function tagasta(param) {
    var title = jQuery(param).parent().prev("td").find("select").val();

    var book = {
        "title": title
    };
    var jsonbook = JSON.stringify(book);
    jQuery.ajax({
        type: "POST",
        data: jsonbook,
        contentType: "application/json;",
        url: "http://localhost:8080/raamatukogu/return",
        success: function(data) {
            jQuery(param).parent().find('button').replaceWith('<button onclick="laenuta(this)" class="laenuta"">Laenuta</button>')
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

function getPeople() {
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: "http://localhost:8080/raamatukogu/people",
        success: function(data) {
            $.each(data, function(key, data_item) {
                console.log(data_item);

                jQuery(".available_people_list").append("<option value="+data_item.id+">" + data_item.name+ "</option>");
            })
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

function get_unavailable_books(){
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: "http://localhost:8080/raamatukogu/unavailable",
        success: function(data) {
             jQuery(".unavailable_book_table tbody").empty();
            $.each(data, function(key, data_item) {
                jQuery(".unavailable_book_table tbody").append("<tr><td>" + data_item.book.title + "</td><td>" + data_item.loanedTo.name + "</td><td>" + data_item.loanedTo.phone + "</td><td><button onclick='laenuta(this)' class='laenuta'>Laenuta</button></td></tr>");
            })
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

jQuery(document).ready(function() {
    get_unavailable_books();
    //get all unavailable books


    //get all available books
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: "http://localhost:8080/raamatukogu/books",
        success: function(data) {
            $.each(data, function(key, data_item) {
                jQuery(".available_book_table tbody").append("<tr><td class='book_title''>" + data_item.title + "</td><td><select class='available_people_list'><option class='start_person_choice'>Choose a name</option></select></td><td><button onclick='laenuta(this)' class='laenuta'>Laenuta</button></td></tr>");
            });
            getPeople();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
    //get all available people that have no book loaned to

});