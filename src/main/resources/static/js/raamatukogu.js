function laenuta(param) {
    var title = jQuery(param).parent().find("span").html();
    var book = {
        "book": {
            "title": title
        },
        loanedTo: {
            "name": "Jaanus",
            "phone": 453534
        }

    };

    var jsonbook = JSON.stringify(book);
    jQuery.ajax({
        type: "POST",
        data: jsonbook,
        contentType: "application/json;",
        url: "http://localhost:8080/raamatukogu/loan",
        success: function(data) {
            jQuery(param).parent().find('button').replaceWith('<button onclick="tagasta(this)" class="laenuta">Tagasta</button>')
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

function tagasta(param) {
    var title = jQuery(param).parent().find("span").html();
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
                jQuery(".available_people_list").append("<option>" + data_item.name + "</option>");
            })
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
}

jQuery(document).ready(function() {

    //get all unavailable books
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: "http://localhost:8080/raamatukogu/unavailable",
        success: function(data) {
            $.each(data, function(key, data_item) {
                console.log(data_item);
                jQuery(".unavailable_book_table tbody").append("<tr><td>" + data_item.book.title + "</td><td>" + data_item.loanedTo.name + "</td><td>" + data_item.loanedTo.phone + "</td><td><button onclick='laenuta(this)' class='laenuta'>Laenuta</button></td></tr>");
            })
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });

    //get all available books
    jQuery.ajax({
        type: "GET",
        dataType: 'JSON',
        url: "http://localhost:8080/raamatukogu/books",
        success: function(data) {
            $.each(data, function(key, data_item) {
                jQuery(".available_book_table tbody").append("<tr><td>" + data_item.title + "</td><td><select class='available_people_list'><option class='start_person_choice'>Choose a name</option></select></td><td><button onclick='laenuta(this)' class='laenuta'>Laenuta</button></td></tr>");
            });
            getPeople();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {}
    });
    //get all available people that have no book loaned to

});