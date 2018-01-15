/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.face.facetracker;

import android.content.Context;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.samples.vision.face.facetracker.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;



    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(16);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(Color.RED);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        //      canvas.drawCircle(x + ID_X_OFFSET, y + ID_Y_OFFSET, face.getWidth()/2, mFacePositionPaint);
       // canvas.drawText("id: " + mFaceId, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);
      //  canvas.drawText("H: " + String.format("%.2f", face.getIsSmilingProbability()), x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
      //  canvas.drawText("RE: " + String.format("%.2f", face.getIsRightEyeOpenProbability()), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
      //  canvas.drawText("LE: " + String.format("%.2f", face.getIsLeftEyeOpenProbability()), x - ID_X_OFFSET*2, y - ID_Y_OFFSET*2, mIdPaint);
      //  canvas.drawText("Smile by: " + DbAct.userSetSmile, x - ID_X_OFFSET*3, y - ID_Y_OFFSET*3, mIdPaint);

        Log.d("SmileSMS", "outside");
//HAVE TO SETUP THESE RANGES FOR DEFAULT CALIBRATIONS

//        if (face.getIsSmilingProbability() > 0.00) {
//            Tweak.uSmile = face.getIsSmilingProbability();
//        }

//        Float f1 = Float.valueOf(DbAct.userSetSmile);



        if (!Chooser.selfCalib){ //Mahwash calibrations will go within this IF..
            Log.d("selfCal", "Outside in false");

            GestureValidity(face.getIsRightEyeOpenProbability(), face.getIsLeftEyeOpenProbability(), face.getIsSmilingProbability());


//            if (Tweak.wait) {
//                Log.d("timeer", "inside Wait");
//                new java.util.Timer().schedule(
//                        new java.util.TimerTask() {
//                            @Override
//                            public void run() {
//                                Tweak.wait = false;
//                                Log.d("timeer", "FaceGraphicTimer");
//
//                            }
//                        },
//                        6000
//                );
//
//
//            }
//            if (face.getIsSmilingProbability() >= 0.600) {
//                Tweak.ivar1 = 1;
//    //            Tweak.uSmile = face.getIsSmilingProbability();
//                Log.d("SmileSMS", "smile ki");
//               // SendSms();
//                String s1 = String.format("%.2f", face.getIsSmilingProbability());
//                Log.d("test", s1);
//
//            }
//            if (face.getIsLeftEyeOpenProbability() < 0.200 && face.getIsLeftEyeOpenProbability() > -1.00) {
//                Tweak.ivar1 = 2;
//                Log.d("SmileSMS", "ankh mari");
//
//                String s1 = String.format("%.2f", face.getIsLeftEyeOpenProbability());
//                Log.d("test", s1+" "+"eyes");
//
//            }
        }

        else { // selfCalib is true//


//            Log.d("selfCal", "Outside");
            if ((face.getIsSmilingProbability() > 0.00) && (face.getIsLeftEyeOpenProbability() > 0.00) && (face.getIsRightEyeOpenProbability() > 0.00) && (!DbAct.isCalibrated)) { //so that -1 isn't detected and initial val is extracted for setting

                Tweak.uSmile = face.getIsSmilingProbability();
                Tweak.uRE = face.getIsRightEyeOpenProbability();
                Tweak.uLE = face.getIsLeftEyeOpenProbability();

                Log.d("selfCal", "Above zero");
            }
            //
            //HAVE TO THINK HOW TO GET THE 8 ROWS AND FIX IT WITH MAHWASH'S ALGO
            //
            //
            if (DbAct.nowValRet) {
                //gesture code in here.

                float f11 = DbAct.retVal[0][0];
                float f12 = DbAct.retVal[0][1];
                float f13 = DbAct.retVal[0][2];

                float f21 = DbAct.retVal[1][0];
                float f22 = DbAct.retVal[1][1];
                float f23 = DbAct.retVal[1][2];

                float f31 = DbAct.retVal[2][0];
                float f32 = DbAct.retVal[2][1];
                float f33 = DbAct.retVal[2][2];

                float f41 = DbAct.retVal[3][0];
                float f42 = DbAct.retVal[3][1];
                float f43 = DbAct.retVal[3][2];

                float f51 = DbAct.retVal[4][0];
                float f52 = DbAct.retVal[4][1];
                float f53 = DbAct.retVal[4][2];

//                float f61 = DbAct.retVal[5][0];
//                float f62 = DbAct.retVal[5][1];
//                float f63 = DbAct.retVal[5][2];
//
//                float f71 = DbAct.retVal[6][0];
//                float f72 = DbAct.retVal[6][1];
//                float f73 = DbAct.retVal[6][2];
//
//                float f81 = DbAct.retVal[7][0];
//                float f82 = DbAct.retVal[7][1];
//                float f83 = DbAct.retVal[7][2];

                //RightE, LeftE, and Smile
                Log.d("1May", Float.toString(f21)+" "+Float.toString(f22)+" "+Float.toString(f23));
                float variation = 0.05f;
                if ((f11-variation) <face.getIsRightEyeOpenProbability() && (f11+variation) > face.getIsRightEyeOpenProbability() && (f12-variation) < face.getIsLeftEyeOpenProbability() && (f12 + variation) > face.getIsLeftEyeOpenProbability() && (f13-variation) < face.getIsSmilingProbability() && (f13 + variation) > face.getIsSmilingProbability() ) { //f11 for gesture 1
                    //g1
                    Tweak.ivar2 = 1;
                }
                if ((f21-variation) <face.getIsRightEyeOpenProbability() && (f21+variation) > face.getIsRightEyeOpenProbability() && (f22-variation) < face.getIsLeftEyeOpenProbability() && (f22 + variation) > face.getIsLeftEyeOpenProbability() && (f23-variation) < face.getIsSmilingProbability() && (f23 + variation) > face.getIsSmilingProbability() ) { //f21 for gesture 2
                    //g2
                    Log.d("1May", "should get 2");
                    Tweak.ivar2 = 2;
                }
                if ((f31-variation) <face.getIsRightEyeOpenProbability() && (f31+variation) > face.getIsRightEyeOpenProbability() && (f32-variation) < face.getIsLeftEyeOpenProbability() && (f32 + variation) > face.getIsLeftEyeOpenProbability() && (f33-variation) < face.getIsSmilingProbability() && (f33 + variation) > face.getIsSmilingProbability() ) { //f31 for gesture 3
                    //g3
                    Tweak.ivar2 = 3;
                }
                if ((f41-variation) <face.getIsRightEyeOpenProbability() && (f41+variation) > face.getIsRightEyeOpenProbability() && (f42-variation) < face.getIsLeftEyeOpenProbability() && (f42 + variation) > face.getIsLeftEyeOpenProbability() && (f43-variation) < face.getIsSmilingProbability() && (f43 + variation) > face.getIsSmilingProbability() ) { //f41 for gesture 4
                    //g4
                    Tweak.ivar2 = 4;
                }
                if ((f51-variation) <face.getIsRightEyeOpenProbability() && (f51+variation) > face.getIsRightEyeOpenProbability() && (f52-variation) < face.getIsLeftEyeOpenProbability() && (f52 + variation) > face.getIsLeftEyeOpenProbability() && (f53-variation) < face.getIsSmilingProbability() && (f53 + variation) > face.getIsSmilingProbability() ) { //f51 for gesture 5
                    //g5
                    Tweak.ivar2 = 6;
                    mBoxPaint.setColor(Color.CYAN);
                }
//                if ((f61-variation) <face.getIsRightEyeOpenProbability() && (f61+variation) > face.getIsRightEyeOpenProbability() && (f62-variation) < face.getIsLeftEyeOpenProbability() && (f62 + variation) > face.getIsLeftEyeOpenProbability() && (f63-variation) < face.getIsSmilingProbability() && (f63 + variation) > face.getIsSmilingProbability() ) { //f61 for gesture 6
//                    //g6
//                    Tweak.ivar2 = 6;
//                }
//                if ((f71-variation) <face.getIsRightEyeOpenProbability() && (f71+variation) > face.getIsRightEyeOpenProbability() && (f72-variation) < face.getIsLeftEyeOpenProbability() && (f72 + variation) > face.getIsLeftEyeOpenProbability() && (f73-variation) < face.getIsSmilingProbability() && (f73 + variation) > face.getIsSmilingProbability() ) { //f71 for gesture 7
//                    //g7
//                    Tweak.ivar2 = 7;
//                }
//                if ((f81-variation) <face.getIsRightEyeOpenProbability() && (f81+variation) > face.getIsRightEyeOpenProbability() && (f82-variation) < face.getIsLeftEyeOpenProbability() && (f82 + variation) > face.getIsLeftEyeOpenProbability() && (f83-variation) < face.getIsSmilingProbability() && (f83 + variation) > face.getIsSmilingProbability() ) { //f81 for gesture 8
//                    //g8
//                    Tweak.ivar2 = 8;
//                }





            }

//            if (DbAct.userSetSmile != null){
//                Float f1 = Float.valueOf(DbAct.userSetSmile); //f1 has now user val stored
//                Log.d("selfCal", DbAct.userSetSmile);
//
//                //Have to make a range first as EXACT match is not possible
//                //OR NOT!! HMM...
//                if (face.getIsSmilingProbability() > f1) {
//                    Tweak.scal = 1; //supposedly this is a component task like BT one
//                }
//
//
//            }




        }


        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        //canvas.drawCircle(xOffset, yOffset, FACE_POSITION_RADIUS, mBoxPaint);
        canvas.drawRect(left, top, right, bottom, mBoxPaint);
    }



    public void showTime(int gesture)
    {

        switch(gesture)
        {
            case 1: Log.w("PLIS", "g1");
                Tweak.ivar1 = 1;
                mBoxPaint.setColor(Color.RED);
                break;
            case 2: Log.w("PLIS", "g2");
                Tweak.ivar1 = 2;
                mBoxPaint.setColor(Color.RED);
                break;
            case 3: Log.w("PLIS", "g3");
                Tweak.ivar1 = 3;
                mBoxPaint.setColor(Color.RED);
                break;
            case 4: Log.w("PLIS", "g4");
                Tweak.ivar1 = 4;
                mBoxPaint.setColor(Color.RED);
                break;
            case 5: Log.w("PLIS", "g5");
                Tweak.ivar1 = 5;
                mBoxPaint.setColor(Color.RED);
                break;
            case 6: Log.w("PLIS", "g6");
                Tweak.ivar1 = 6;
                mBoxPaint.setColor(Color.GREEN);
                break;
            case 7: Log.w("PLIS", "g7");
                Tweak.ivar1 = 7;
                mBoxPaint.setColor(Color.RED);
                break;
            case 8: Log.w("PLIS", "g8");
                Tweak.ivar1 = 8;
                mBoxPaint.setColor(Color.RED);
                break;
            default: Log.w("PLIS", "kaddu");
                Tweak.ivar1 = 1;
                mBoxPaint.setColor(Color.RED);
        }

    }

    public void GestureValidity(float R, float L, float S) //Check: -1 >x<= 1
    {
        if(((R > -1) && (R <= 1)) && ((L > -1) && (L <= 1)) && ((S > -1) && (S <= 1)))
        {
            int a = KonsaGestureHaiYeh(R, L, S);
            showTime(a);
        }
        else
        {
//            print("Sorry Bro, TRY AGAIN");
        }

    }

    public int KonsaGestureHaiYeh(float R, float L, float S)
    {
        if(S <= 0.45)
        {
            if(R <= 0.38)
            {
                if(L <= 0.32)
                {
                    if(R <= 0.28) {
                        if (S <= 0.28) {
                            if (L <= 0.04) {
                                if (S <= 0.02) {
                                    return 3;
                                } else {
                                    if (S <= 0.05) {
                                        return 5;
                                    } else {
                                        return 3;
                                    }
                                }
                            }
                        } else {
                            if (R <= 0.19) {
                                return 3;
                            } else {
                                return 8;
                            }
                        }
                    }
                    else
                    {
                        if(L <= 0.04)
                        {
                            return 3;
                        }
                        else
                        {
                            return 7;
                        }
                    }
                }
                else
                {
                    if(L <= 0.44)
                    {
                        if(S <= 0.02)
                        {
                            return 5;
                        }
                        else
                        {
                            if(S <= 0.1)
                            {
                                return 7;
                            }
                            else
                            {
                                return 3;
                            }
                        }
                    }
                    else
                    {
                        if(S <= 0.32)
                        {
                            if(L <= 0.56)
                            {
                                return 5;
                            }
                            else
                            {
                                if(R <= 0.33)
                                {
                                    if(L <= 0.92)
                                    {
                                        if(L <= 0.68)
                                        {
                                            return 5;
                                        }
                                        else
                                        {
                                            if(S <= 0.02)
                                            {
                                                return 5;
                                            }
                                            else
                                            {
                                                if(S <= 0.14)
                                                {
                                                    return 1;
                                                }
                                                else
                                                {
                                                    return 5;
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        return 5;
                                    }
                                }
                                else
                                {
                                    if(S <= 0.2)
                                    {
                                        if(S <= 0.06)
                                        {
                                            return 5;
                                        }
                                        else
                                        {
                                            return 2;
                                        }
                                    }
                                    else
                                    {
                                        return 5;
                                    }
                                }
                            }
                        }
                        else
                        {
                            if(S <= 0.42)
                            {
                                return 3;
                            }
                            else
                            {
                                return 5;
                            }
                        }
                    }
                }
            }
            else
            {
                if(L <= 0.60)
                {
                    if(S <= 0.3)
                    {
                        if(R <= 0.58)
                        {
                            if(L <= 0.45)
                            {
                                if(S <= 0.11)
                                {
                                    return 7;
                                }
                                else
                                {
                                    if(R <= 0.47)
                                    {
                                        return 5;
                                    }
                                    else
                                    {
                                        return 7;
                                    }
                                }
                            }
                            else
                            {
                                if(L <= 0.54)
                                {
                                    return 3;
                                }
                                else
                                {
                                    return 7;
                                }
                            }
                        }
                        else
                        {
                            return 7;
                        }
                    }
                    else
                    {
                        if(R <= 0.65)
                        {
                            if(S <= 0.33)
                            {
                                return 8;
                            }
                            else
                            {
                                return 5;
                            }
                        }
                        else
                        {
                            return 7;
                        }
                    }
                }
                else
                {
                    if(R <= 0.83)
                    {
                        if(R <= 0.45)
                        {
                            return 1;
                        }
                        else
                        {
                            if(R <= 0.5)
                            {
                                return 5;
                            }
                            else
                            {
                                if(S <= 0.23)
                                {
                                    if(R <= 0.81)
                                    {
                                        if(S <= 0.02)
                                        {
                                            return 1;
                                        }
                                        else
                                        {
                                            return 5;
                                        }
                                    }
                                    else
                                    {
                                        return 5;
                                    }
                                }
                                else
                                {
                                    return 2;
                                }
                            }
                        }
                    }
                    else
                    {
                        return 1;
                    }
                }
            }
        }
        else
        {
            if(R <= 0.425)
            {
                if(L <= 0.26)
                {
                    if(R <= .165)
                    {
                        return 4;
                    }
                    else
                    {
                        if(S <= 0.915)
                        {
                            return 5;
                        }
                        else
                        {
                            if(S<=0.955)
                            {
                                return 4;
                            }
                            else
                            {
                                if(R<=0.22)
                                {
                                    if(L<=0.05)
                                    {
                                        return 4;
                                    }
                                    else
                                    {
                                        return 5;
                                    }
                                }
                                else
                                {
                                    if(S <= 0.975)
                                    {
                                        return 8;
                                    }
                                    else
                                    {
                                        if(L<=0.105)
                                        {
                                            return 4;
                                        }
                                        else
                                        {
                                            return 8;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
                else
                {
                    if(L<= 0.43)
                    {
                        if(R<=0.32)
                        {
                            if(R<=0.08)
                            {
                                return 6;
                            }
                            else
                            {
                                return 8;
                            }
                        }
                        else
                        {
                            return 6;

                        }
                    }
                    else
                    {
                        return 6;
                    }
                }
            }
            else
            {
                if(L<= 0.7)
                {
                    if(L<=0.56)
                    {
                        return 8;
                    }
                    else
                    {
                        if(L<=0.58)
                        {
                            return 2;
                        }
                        else
                        {
                            if(L<=0.64)
                            {
                                return 8;
                            }
                            else
                            {
                                return 3;
                            }
                        }
                    }
                }
                else
                {
                    return 2;
                }
            }

        }
        return 0;
    }
}