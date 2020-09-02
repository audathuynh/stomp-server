package org.maxsure.demo.data;

import lombok.Data;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Data
public class BaseCommand {

    private String id;
    private CommandType commandType;
    private String username;
    private String workstationName;

}
