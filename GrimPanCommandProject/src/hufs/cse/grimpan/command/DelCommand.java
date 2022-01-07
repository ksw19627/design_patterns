/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.command; //Àü
import hufs.cse.grimpan.core.GrimPanModel;
import hufs.cse.grimpan.shape.GrimShape;
/**
 * @author cskim
 *
 */
public class DelCommand implements Command {

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#execute()
	 */
	int selindex;

	GrimPanModel model = null;
	GrimShape savedGrimShape = null;
	GrimShape deletedGrimShape = null;

	public DelCommand(GrimPanModel model, GrimShape grimShape){
		this.model = model;
		this.deletedGrimShape = grimShape;
		this.selindex = model.getSelectedShape();
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		savedGrimShape = model.shapeList.remove(model.getSelectedShape());
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#undo()
	 */
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		if(selindex != -1){
			model.shapeList.add(selindex, savedGrimShape);
			selindex = -1;
		}
	}

}
