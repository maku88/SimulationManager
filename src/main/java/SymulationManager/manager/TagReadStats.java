package SymulationManager.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 25.08.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
@Data
@ToString
@AllArgsConstructor
public class TagReadStats {

    private String tagID;
    private double probability;
    private long duration;
    private boolean isCorrect;

}
