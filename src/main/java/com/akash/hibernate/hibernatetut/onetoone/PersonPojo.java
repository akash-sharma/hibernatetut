package com.akash.hibernate.hibernatetut.onetoone;

public class PersonPojo {
		
		private String personName;
		private Integer id;
		private String vehicleName;
		
		public String getPersonName() {
			return personName;
		}

		public void setPersonName(String personName) {
			this.personName = personName;
		}
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getVehicleName() {
			return vehicleName;
		}

		public void setVehicleName(String vehicleName) {
			this.vehicleName = vehicleName;
		}

		public PersonPojo(String personName,Integer id, String vehicleName) {
			
			this.personName=personName;
			this.id=id;
			this.vehicleName=vehicleName;
		}
	}