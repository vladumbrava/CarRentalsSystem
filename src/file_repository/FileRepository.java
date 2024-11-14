package file_repository;

import domain.Identifiable;
import repository.MemoryRepository;

public abstract class FileRepository<ID,T extends Identifiable<ID>> extends MemoryRepository<ID,T> {
    protected String fileName;
    protected String writeFileName;

    public FileRepository(String fileName, String writeFileName) {
        this.fileName = fileName;
        this.writeFileName = writeFileName;
        this.readFromFile();
    }

    abstract void readFromFile();
    abstract void writeToFile();

    @Override
    public void add(ID idToAdd, T objectToAdd) {
        super.add(idToAdd, objectToAdd);
        this.writeToFile();
    }
}
