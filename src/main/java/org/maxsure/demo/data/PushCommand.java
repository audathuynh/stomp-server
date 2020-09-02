package org.maxsure.demo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PushCommand extends BaseCommand {

    private String entryData;

}
