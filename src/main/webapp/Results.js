/**
 * Created by Ali_Iman on 3/17/17.
 */

var completeResult;
var done = false;

function setup() {
    document.getElementsByTagName("body")[0].innerHTML = document.getElementsByTagName("body")[0].innerHTML;
    if (!done) {
        done = true;
        completeResult = document.getElementById("results");
        document.getElementsByTagName("body")[0].innerHTML = document.getElementsByTagName("body")[0].innerHTML;
    }
}

setup();

function filterSeatClass() {
    document.getElementById("results").innerHTML = completeResult.innerHTML;

    var seatClassName = document.getElementById("seatClassFilter").value;
    var airlineName = document.getElementById("airlineFilter").value;

    var seatClassFilter = seatClassName.length == 1;
    var airlineFilter = airlineName.length != 0;

    if (seatClassFilter || airlineFilter) {
        var finalResultsHtml = "";
        var elements = document.getElementsByClassName("results-form");

        for(var i = 0; i < elements.length; i++) {
            var nodes = elements[i].getElementsByClassName("col-md-6 place-middle font-small lbl-color-light-gray pull-right");
            var node = nodes[0];
            var tempSeatClass = node.getElementsByTagName("span")[3].innerHTML.replace(/\s/g,'');
            nodes = elements[i].getElementsByClassName("col-md-3 place-middle font-large lbl-color-black pull-right");
            node = nodes[0];
            var tempAirline = (node.innerHTML.match(/\S+/g) || [])[0];

            if((!seatClassFilter || (seatClassName == tempSeatClass)) && (!airlineFilter || (airlineName == tempAirline)))
                finalResultsHtml += elements[i].outerHTML;
        }

        document.getElementById("results").innerHTML = finalResultsHtml;
    }
}

function ascendingCompare(a, b) {
    var nodes = a.getElementsByClassName("col-md-12 place-middle");
    var node = nodes[0];
    var priceA = node.getElementsByTagName("span")[0].innerHTML;

    nodes = b.getElementsByClassName("col-md-12 place-middle");
    node = nodes[0];
    var priceB = node.getElementsByTagName("span")[0].innerHTML;

    return priceA - priceB;
}

function ascendingSortBasedOnPrice() {
    var elements = document.getElementsByClassName("results-form");
    elements = Array.prototype.slice.call(elements, 0);
    elements.sort(ascendingCompare);
    var finalResultsHtml = "";
    for(var i = 0; i < elements.length; i++)
        finalResultsHtml += elements[i].outerHTML;
    document.getElementById("results").innerHTML = finalResultsHtml;
}

function descendingCompare(a, b) {
    var nodes = a.getElementsByClassName("col-md-12 place-middle");
    var node = nodes[0];
    var priceA = node.getElementsByTagName("span")[0].innerHTML;

    nodes = b.getElementsByClassName("col-md-12 place-middle");
    node = nodes[0];
    var priceB = node.getElementsByTagName("span")[0].innerHTML;

    return priceB - priceA;
}

function descendingSortBasedOnPrice() {
    var elements = document.getElementsByClassName("results-form");
    elements = Array.prototype.slice.call(elements, 0);
    elements.sort(descendingCompare);
    var finalResultsHtml = "";
    for(var i = 0; i < elements.length; i++)
        finalResultsHtml += elements[i].outerHTML;
    document.getElementById("results").innerHTML = finalResultsHtml;
}