/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.command;

import hufs.cse.grimpan.core.GrimPanModel;
import hufs.cse.grimpan.shape.GrimShape;

/**
 * @author cskim
 *
 */
public class MoveCommand implements Command {

	GrimPanModel model = null;
	GrimShape savedGrimShape = null;
	GrimShape movedGrimShape = null;
	public MoveCommand(GrimPanModel model, GrimShape grimShape){
		this.model = model;
		this.savedGrimShape = grimShape;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#execute()
	 */
	@Override
	public void execute() {
		movedGrimShape = model.shapeList.get(model.getSelectedShape());
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#undo()
	 */
	@Override
	public void undo() {
		int selIndex = model.shapeList.indexOf(movedGrimShape);
		if (selIndex != -1){
			model.shapeList.set(selIndex, savedGrimShape);
		}
	}

}
