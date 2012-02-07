package com.netnumeri.shared.finance.finpojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class User implements Serializable {
    private static final long serialVersionUID = 2019181020074975689L;

    private int lockCount;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String department;
    private String jobTitle;

    private Set<Portfolio> portfolios = new HashSet<Portfolio>();

    public Set<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(Set<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public User() {
    }

    public int getLockCount() {
        return lockCount;
    }

    public void setLockCount(int lock) {
        this.lockCount = lock;
    }

    public boolean isLocked() {
        return lockCount >= 3;
    }

    public void setLocked(boolean locked) {
        this.lockCount = locked ? 3 : 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


//	public Set<XEdtUserOpco> getActiveOpcos() {
//		Set<XEdtUserOpco> activeOpcos = new HashSet<XEdtUserOpco>();
//		for (XEdtUserOpco xup : relatedOpcos) {
//			if (xup.getOpco().isActive()) {
//				activeOpcos.add(xup);
//			}
//		}
//		return activeOpcos;
//	}
//
//	public String getOpcosIds() {
//		List<String> list = new ArrayList<String>();
//		if (relatedOpcos != null) {
//			for (Iterator<XEdtUserOpco> itP = relatedOpcos.iterator(); itP.hasNext();) {
//				Opco p = itP.next().getOpco();
//				if (p != null && p.isActive()) {
//					list.add(p.getId().toString());
//				}
//			}
//		}
//		return GlobalUtils.toCsv(list);
//	}
//
//	public String getOpcosNames() {
//		List<String> list = new ArrayList<String>();
//		if (relatedOpcos != null) {
//			for (Iterator<XEdtUserOpco> itP = relatedOpcos.iterator(); itP.hasNext();) {
//				Opco p = itP.next().getOpco();
//				if (p != null && p.isActive()) {
//					list.add(p.getCountryName());
//				}
//			}
//		}
//		return GlobalUtils.toCsv(list);
//	}
//
//	public boolean isAssociatedToOpco(Opco opco) {
//		for (XEdtUserOpco xup : relatedOpcos) {
//			if (xup.getOpco().isEquals(opco)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public void addOpco(Opco opco) {
//		new XEdtUserOpco(this, opco);
//	}
//
//	protected void addXEdtUserOpco(XEdtUserOpco xup) {
//		relatedOpcos.add(xup);
//	}
//
//	public void removeOpco(Opco opco) {
//		for (Iterator<XEdtUserOpco> itX = relatedOpcos.iterator(); itX.hasNext();) {
//			XEdtUserOpco xup = itX.next();
//			if (xup.getOpco().isEquals(opco)) {
//				itX.remove();
//				opco.removeUser(this);
//				return;
//			}
//		}
//	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.getUsername()) : user.getUsername() != null) return false;

        return true;
    }

    @Override
    public String toString() {
        String ret = getClass().getSimpleName() + ":";
        ret += " username=" + username;
        ret += " firstName=" + firstName;
        ret += " lastName=" + lastName;
        ret += " email=" + email;
        return ret;
    }

}
