package com.bol.interview.mancalaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private List<Pit> pitList;
    private Player player;
    private Integer score;

    public String getPlayerUserName() {
        return getPlayer().getUserName();
    }

    @Data
    @AllArgsConstructor
    public static class Pit {
        private Integer order;
        private Integer value;

        public void setValuePlus(@NonNull Integer valuePlus) {
            value += valuePlus;
        }
    }
}
