
export const fetchAds = async (page, limit) => {
    try {
        const response = await fetch(`http://localhost:5001/ads?page=${page}&limit=${limit}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        throw new Error(error.message);
    }
};
