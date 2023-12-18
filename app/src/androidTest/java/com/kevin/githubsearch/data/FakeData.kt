package com.kevin.githubsearch.data

import com.kevin.githubsearch.data.model.GitHubRepo
import com.kevin.githubsearch.data.model.GitHubUserInfo

val fakeReposes = listOf<GitHubRepo>(
        GitHubRepo(132935648,
                "boysenberry-repo-1",
                14,
                "Testing",
                "octocat"
        ),
        GitHubRepo(18221276,
                "git-consortium",
                104,
                "This repo is for demonstration purposes",
                "octocat"
        ),
        GitHubRepo(20978623,
                "hello-worId",
                193,
                "My first repository on",
                "octocat"
        ),
        GitHubRepo(17881631,
                "octocat.github.io",
                380,
                "",
                "octocat"
        ),
        GitHubRepo(1300192,
                "Spoon-Knife",
                140473,
                "This repo is for demonstration purposes only.",
                "octocat"
        )
)

val fakeReposesNoStar = listOf<GitHubRepo>(
        GitHubRepo(132935648,
                "boysenberry-repo-1",
                14,
                "Testing",
                "octocat"
        ),
        GitHubRepo(18221276,
                "git-consortium",
                104,
                "This repo is for demonstration purposes",
                "octocat"
        ),
        GitHubRepo(20978623,
                "hello-worId",
                193,
                "My first repository on",
                "octocat"
        ),
        GitHubRepo(17881631,
                "octocat.github.io",
                380,
                "",
                "octocat"
        ),
        GitHubRepo(1300192,
                "Spoon-Knife",
                1,
                "This repo is for demonstration purposes only.",
                "octocat"
        )
)

val fakeUserInfo = GitHubUserInfo("583231",
        "https://avatars.githubusercontent.com/u/583231?v=4",
        "The Octocat",
        "octocat"
)