//    final double[] tStart = { 0, 0 };
//    final double[] iStart = { 0, 120 };
//    final double[] sStart = { 0, 190 };
//    final double[] zStart = { 0, 310 };
//    final double[] jStart = { 0, 430 };
//    final double[] lStart = { 0, 600 };
//    final double[] oStart = { 0, 770 };
//
//    private void setCoords (double [] startCoords, double[][] coords) {
//
//        for (int i = 0; i < coords.length; i++){
//            coords[i][0] = coords[i][0] + startCoords[0];
//            coords[i][1] = coords[i][1] + startCoords[1];
//        }
//    }
//        for (int i = 0; i < pieces.length; i++) {
//        if (i == 1) {
//                AffineTransform rotator = new AffineTransform();
//                rotator.rotate(Math.PI, 75, 50);
//                pieces[1].transform(rotator);
//            } else if (i == 2) {
//                AffineTransform rotator = new AffineTransform();
//                rotator.rotate(-Math.PI/2, 50, 50);
//                pieces[2].transform(rotator);
//            } else if (i == 3) {
//                AffineTransform rotator = new AffineTransform();
//                rotator.rotate(Math.PI/2, 100, 50);
//                pieces[1].transform(rotator);
//
//                AffineTransform rotator1a = new AffineTransform();
//                rotator1a.rotate(-Math.PI/2, 100, 50);
//                pieces[1].transform(rotator1a);
//
//                AffineTransform rotator2 = new AffineTransform();
//                rotator2.rotate(Math.PI/2, 75, 25);
//                pieces[1].transform(rotator2);
//
//                    AffineTransform rotator7 = new AffineTransform();
//                    rotator7.rotate(-Math.PI/2, 75, 25);
//                    pieces[1].transform(rotator7);
//
//                AffineTransform rotator3 = new AffineTransform();
//                rotator3.rotate(Math.PI/2, 75, 25);
//                pieces[1].transform(rotator3);
//
//                  AffineTransform rotator6 = new AffineTransform();
//                  rotator6.rotate(-Math.PI/2, 75, 25);
//                  pieces[1].transform(rotator6);
//
//                AffineTransform rotator4 = new AffineTransform();
//                rotator4.rotate(Math.PI/2, 50, 50);
//                pieces[1].transform(rotator4);
//
//                  AffineTransform rotator5 = new AffineTransform();
//                  rotator5.rotate(-Math.PI/2, 50, 50);
//                  pieces[1].transform(rotator5);
//
//
//        AffineTransform rotator1b = new AffineTransform();
//        rotator1b.rotate(-Math.PI/2, 50, 50);
//        pieces[1].transform(rotator1b);
//
//        AffineTransform rotator2b = new AffineTransform();
//        rotator2b.rotate(-Math.PI/2, 75, 25);
//        pieces[1].transform(rotator2b);
//
//        AffineTransform rotator3b = new AffineTransform();
//        rotator3b.rotate(-Math.PI/2, 75, 25);
//
//        AffineTransform rotator4b = new AffineTransform();
//        rotator4b.rotate(-Math.PI/2, 75, 25);
//        pieces[1].transform(rotator4b);
//
//
//        }
//
//
//        draw(g2d, colors[i], pieces[i]);
//        }
//
