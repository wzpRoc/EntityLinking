function leftFill(s, totalLength, c) {
    for(var i=0; i<totalLength - s.length; i++) {
        s = c + s;
    }
    return s;
}