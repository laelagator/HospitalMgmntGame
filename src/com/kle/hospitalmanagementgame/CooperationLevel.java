package com.kle.hospitalmanagementgame;

import java.util.Random;

public class CooperationLevel {
	public static boolean highCooperation = true;
	/**
	 * Carries out High Cooperation or Low Cooperation behavior in the non-player hospital.
	 * @param currentResources
	 * @param otherHospitalResources
	 * @return
	 */
	public static boolean fulfillRequestAlgorithm(int currentResources, int otherHospitalResources){
		if(otherHospitalResources == 0){
			return false;
		}

		//new way 5/14/14
		/*
	  		--Cooperative--
		     0-2, 100% accept
		     3-4, 50% accept
		     5-6, 25% accept
		     --Competitive--
		     0-2, 50% accept
		     3-4, 25% accept
		     5-6, 0% accept
		 */
		int total = HospitalPeople.npcPatientA + HospitalPeople.npcPatientB;
		Random random = new Random();

		if(highCooperation){
			if(total >= 0 && total <= 2)
				return true;
			else if(total > 2 && total <= 4){
				int rand = random.nextInt(2);
				if(rand == 0)
					return true;
				else
					return false;
			}else{
				int rand = random.nextInt(4);
				if(rand == 0)
					return true;
				else
					return false;
			}
		}else{
			if(total >= 0 && total <= 2){
				int rand = random.nextInt(2);
				if(rand == 0)
					return true;
				else
					return false;
			}else if(total > 2 && total <= 4){
				int rand = random.nextInt(4);
				if(rand == 0)
					return true;
				else
					return false;
			}else{
				return false;
			}
		}
	}

}
