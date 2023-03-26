package it.polimi.myshelfie.model.goals;

import it.polimi.myshelfie.model.ItemTile;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalFactoryTest {

    @Test
    void getPersonalGoals() {
        for(int i = 1 ; i < 6; i++){
            List<PersonalGoal> a = PersonalGoalFactory.getDistinctPersonalGoals(i,new Dimension(5,6),2,0);
            assertEquals(i*2,a.size());
            for(int j = 0; j < a.size()-1 ;j++) assertNotEquals(a.get(j),a.get(j+1));
            if(i == 1) for(int j = 0; j < a.size()-1 ;j++) assertFalse(matrixEqual(a.get(j).getGoal(),a.get(j+1).getGoal()));
        }
        for(int i = 1 ; i < 4; i++){
            List<PersonalGoal> a = PersonalGoalFactory.getDistinctPersonalGoals(i,new Dimension(5,6),3,0);
            assertEquals(i*3,a.size());
            for(int j = 0; j < a.size()-1 ;j++) assertNotEquals(a.get(j),a.get(j+1));
            if(i == 1) for(int j = 0; j < a.size()-1 ;j++) assertFalse(matrixEqual(a.get(j).getGoal(),a.get(j+1).getGoal()));
        }
        for(int i = 1 ; i < 3; i++){
            List<PersonalGoal> a = PersonalGoalFactory.getDistinctPersonalGoals(i,new Dimension(5,6),4,0);
            assertEquals(i*4,a.size());
            for(int j = 0; j < a.size()-1 ;j++) assertNotEquals(a.get(j),a.get(j+1));
            if(i == 1) for(int j = 0; j < a.size()-1 ;j++) assertFalse(matrixEqual(a.get(j).getGoal(),a.get(j+1).getGoal()));
        }
        List<PersonalGoal> a = PersonalGoalFactory.getDistinctPersonalGoals(1,new Dimension(5,6),30,0);
        for(int i = 1 ; i < 10; i++){
            a = PersonalGoalFactory.getDistinctPersonalGoals(i,new Dimension(10+i,10+i),5,0.1);
            assertEquals(i*5,a.size());
            for(int j = 0; j < a.size()-1 ;j++) assertNotEquals(a.get(j),a.get(j+1));
        }
    }

    private boolean matrixEqual(ItemTile[][] A, ItemTile[][] B){
        if(A.length!=B.length) return false;
        if(A[0].length!=B[0].length) return false;
        for(int y = 0; y<A.length; y++){
            for(int x = 0; x<A[0].length; x++){
                if(A[y][x].getType()!=B[y][x].getType()) return false;
            }
        }
        return true;
    }
}