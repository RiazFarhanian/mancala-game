package com.bol.interview.mancalaservice.model;

import com.bol.interview.mancalaservice.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PitView {
    private Board.Pit pit;
    private String playerUserName;
    private Boolean isScorePit=false;

    public Integer getOrder() {
        return pit.getOrder();
    }

    public Integer getValue() {
        return pit.getValue();
    }

    public void setValuePlus(@NonNull Integer valuePlus) {
        pit.setValuePlus(valuePlus);
    }

}
