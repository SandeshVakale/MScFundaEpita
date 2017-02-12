package fr.epita.iam.problem;

public class UpdateException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String updateFault;
	
	/**
	 * @param message
	 */
	public UpdateException(String message) {
		this.updateFault = message;
	}

	/**
	 * @return
	 */
	public String getUpdateFault() {
		return updateFault;
	}

}
