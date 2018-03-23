package com.rensq.ordero.service.exceptions;

public class EmptyRequestException extends OrderoException {
    public EmptyRequestException(String resource, CrudAction crudAction) {
        super(String.format("You didn't provide a %s for  %s", resource, crudAction.getLabel()));
    }

    public enum CrudAction {

        CREATE("creation"),
        READ("retrieving"),
        UPDATE("updating"),
        DELETE("deletion");

        private String label;

        CrudAction(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
