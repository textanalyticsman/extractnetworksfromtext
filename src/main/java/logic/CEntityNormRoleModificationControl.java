/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author osboxes
 */
public class CEntityNormRoleModificationControl {
    
    /*
    This class is used to hel us to control the modification of entity types
    on raw entities through the GUI called EntitiesRevisionAndFixing
    */
    
    private String oldRole;
    private String newRole;
    private String entityName;



    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    
    public CEntityNormRoleModificationControl(String entityName,String oldRole, String newRole)
    {
        this.entityName=entityName;
        this.oldRole=oldRole;
        this.newRole=newRole;
    }

    public String getOldRole() {
        return oldRole;
    }
    
    public void setOldRole(String oldRole) {
        this.oldRole = oldRole;
    }

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
    
}