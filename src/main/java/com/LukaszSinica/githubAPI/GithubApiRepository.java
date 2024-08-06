package com.LukaszSinica.githubAPI;

import java.util.List;

public class GithubApiRepository {

	private String name;
	private Owner owner;
	private List<Branch> branches;
	public GithubApiRepository(String name, Owner owner, List<Branch> branches) {
		super();
		this.name = name;
		this.owner = owner;
		this.branches = branches;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public List<Branch> getBranches() {
		return branches;
	}
	public void setBranch(List<Branch> branches) {
		this.branches = branches;
	}

}
