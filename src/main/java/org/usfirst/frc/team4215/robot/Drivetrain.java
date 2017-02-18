

			flWheel.changeControlMode(controlMode);
			frWheel.changeControlMode(controlMode);
			blWheel.changeControlMode(controlMode);
			brWheel.changeControlMode(controlMode);
			
			System.out.println("LF: " + lFront + "||LB:" + lBack + "||FR:" + rFront + "||RB:" + rBack);
			flWheel.set(-lFront);
			blWheel.set(-lBack);

		public void drive(double left, double right, double strafe, boolean IsStrafing){
			if (!IsStrafing){
				Go(left,left,right,right);
			}
			
			if (IsStrafing){
				Go(strafe,-strafe,-strafe,strafe);
			}
	
}
	
