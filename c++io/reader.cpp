//reader.cpp

#include <iostream>
#include <fstream>
#include <string>

int main()
{
	std::ifstream readingTarget;
	readingTarget.open("target.txt");
	for (std::string line;getline(readingTarget,line);std::cout<<line<<"\n")
	{}
	readingTarget.close();
}
