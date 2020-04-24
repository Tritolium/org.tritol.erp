package org.tritol.erp.controlling.dialog;

import org.tritol.erp.application.dialog.AbstractDialog;
import org.tritol.erp.controlling.Controller;

public class AbstractDialogController {
	protected Controller _parent;
	protected AbstractDialog _view;
	
	public AbstractDialogController(Controller parent) {
		_parent = parent;
	}
}
