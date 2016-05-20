function daysBetween(DateOne,DateTwo)
{
    var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));
    var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);
    var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));

    var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));
    var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);
    var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));

    var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);
    return Math.abs(cha);
}



function addDay(startDate, days) {
    var startDateObj = Date.parse(startDate);
    var endDateObj = new Date(startDateObj + days * 86400000);

    return toYYYY_MM_DD(endDateObj);
}


function toYYYY_MM_DD(date) {
    return (1900 + date.getYear())
        +"-"+leftFill((date.getMonth()+1), 2, '0')
        +"-"+leftFill(date.getDate(), 2, '0');
}


